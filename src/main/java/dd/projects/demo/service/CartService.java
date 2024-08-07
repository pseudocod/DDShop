package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Cart.CartCreateRequestDto;
import dd.projects.demo.domain.dto.Cart.CartResponseDto;
import dd.projects.demo.domain.dto.CartEntry.CartEntryCreateRequestDto;
import dd.projects.demo.domain.entitiy.Cart;
import dd.projects.demo.domain.entitiy.CartEntry;
import dd.projects.demo.domain.entitiy.Product;
import dd.projects.demo.domain.entitiy.User;
import dd.projects.demo.mappers.CartEntryMapper;
import dd.projects.demo.mappers.CartMapper;
import dd.projects.demo.repository.CartEntryRepository;
import dd.projects.demo.repository.CartRepository;
import dd.projects.demo.repository.ProductRepository;
import dd.projects.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartEntryRepository cartEntryRepository;
    Logger log = org.apache.logging.log4j.LogManager.getLogger(CartService.class);

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartEntryRepository cartEntryRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    public CartResponseDto createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(BigDecimal.ZERO);

        if(user.getUserCarts() == null) {
            user.setUserCarts(new ArrayList<>());
        }

        Cart savedCart = cartRepository.save(cart);
        user.getUserCarts().add(savedCart);
        userRepository.save(user);
        return CartMapper.INSTANCE.toCartResponseDto(savedCart);
    }

    public CartResponseDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        return CartMapper.INSTANCE.toCartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto addCartEntry(Long cartId, CartEntryCreateRequestDto cartEntryCreateRequestDto) {
        log.info("Adding cart entry: cartId={}, productId={}, quantity={}", cartId, cartEntryCreateRequestDto.getProductId(), cartEntryCreateRequestDto.getQuantity());
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        log.info("Cart found: {}", cart.getId());
        Product product = productRepository.findById(cartEntryCreateRequestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        log.info("Product found: {}", product.getName());

        CartEntry existingCartEntry = cartEntryRepository.findCartEntryByProductAndCart(product, cart);
        log.info("Existing cart entry: {}", existingCartEntry != null ? existingCartEntry.getId() : "None");

        if (product.getAvailableQuantity() < cartEntryCreateRequestDto.getQuantity()) {
            log.error("Not enough products in stock: requested={}, available={}", cartEntryCreateRequestDto.getQuantity(), product.getAvailableQuantity());
            throw new IllegalArgumentException("Not enough products in stock");
        }

        if (existingCartEntry != null) {
            log.info("Updating existing cart entry for productId={}, cartId={}", cartEntryCreateRequestDto.getProductId(), cartId);
            updateExistingCartEntry(cartEntryCreateRequestDto, existingCartEntry, product);
        } else {
            log.info("Adding new cart entry for productId={}, cartId={}", cartEntryCreateRequestDto.getProductId(), cartId);
            addNewCartEntry(cartEntryCreateRequestDto, cart, product);
        }

        updateCartTotalPrice(cart);
        Cart updatedCart = cartRepository.save(cart);
        log.info("Cart updated successfully: cartId={}", cartId);

        return CartMapper.INSTANCE.toCartResponseDto(updatedCart);
    }

    private void addNewCartEntry(CartEntryCreateRequestDto cartEntryCreateRequestDto, Cart cart, Product product) {
        CartEntry cartEntry = CartEntryMapper.INSTANCE.toEntity(cartEntryCreateRequestDto);
        cartEntry.setPricePerPiece(product.getPrice());
        cartEntry.setTotalPriceEntry(product.getPrice().multiply(BigDecimal.valueOf(cartEntryCreateRequestDto.getQuantity())));
        cartEntry.setCart(cart);
        cartEntry.setProduct(product);
        if (cart.getCartEntries() == null) {
            cart.setCartEntries(new ArrayList<>());
        }
        log.info("New cart entry: {}", cartEntry);
        cart.getCartEntries().add(cartEntry);
        cartEntryRepository.save(cartEntry);
        log.info("New cart entry added: {}", cartEntry.getId());
    }

    private void updateExistingCartEntry(CartEntryCreateRequestDto cartEntryCreateRequestDto, CartEntry existingCartEntry, Product product) {
        int newQuantity = existingCartEntry.getQuantity() + cartEntryCreateRequestDto.getQuantity();
        if (product.getAvailableQuantity() < newQuantity) {
            log.error("Not enough products in stock: requested={}, available={}", newQuantity, product.getAvailableQuantity());
            throw new IllegalArgumentException("Not enough products in stock");
        }
        existingCartEntry.setQuantity(newQuantity);
        if (existingCartEntry.getPricePerPiece() == null) {
            existingCartEntry.setPricePerPiece(product.getPrice());
        }
        existingCartEntry.setTotalPriceEntry(existingCartEntry.getPricePerPiece().multiply(BigDecimal.valueOf(newQuantity)));
        cartEntryRepository.save(existingCartEntry);
        log.info("Existing cart entry updated: {}", existingCartEntry.getId());
    }

    private void updateCartTotalPrice(Cart cart) {
        if (cart.getCartEntries() == null) {
            cart.setTotalPrice(BigDecimal.ZERO);
            return;
        }

        BigDecimal totalPrice = cart.getCartEntries().stream()
                .map(CartEntry::getTotalPriceEntry)
                .filter(totalPriceEntry -> totalPriceEntry != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
        log.info("Cart total price updated: {}", totalPrice);
    }
}