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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartEntryRepository cartEntryRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartEntryRepository cartEntryRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    public CartResponseDto createCart(Long userId, CartCreateRequestDto cartCreateRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        cartCreateRequestDto.setUserId(userId);
        cartCreateRequestDto.setTotalPrice(BigDecimal.ZERO);
        Cart cart = CartMapper.INSTANCE.toEntity(cartCreateRequestDto);

        Cart savedCart = cartRepository.save(cart);

        return CartMapper.INSTANCE.toCartResponseDto(savedCart);
    }

    public CartResponseDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        return CartMapper.INSTANCE.toCartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto addCartEntry(CartEntryCreateRequestDto cartEntryCreateRequestDto) {
        Cart cart = cartRepository.findById(cartEntryCreateRequestDto.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        Product product = productRepository.findById(cartEntryCreateRequestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartEntry existingCartEntry = cartEntryRepository.findCartEntryByProductAndCart(product.getId(), cart.getId());

        if (product.getAvailableQuantity() < cartEntryCreateRequestDto.getQuantity()) {
            throw new IllegalArgumentException("Not enough products in stock");
        }

        if (existingCartEntry != null) {
            updateExistingCartEntry(cartEntryCreateRequestDto, existingCartEntry, product);
        } else {
            addNewCartEntry(cartEntryCreateRequestDto, cart, product);
        }

        updateCartTotalPrice(cart);
        Cart updatedCart = cartRepository.save(cart);

        return CartMapper.INSTANCE.toCartResponseDto(updatedCart);
    }

    private void addNewCartEntry(CartEntryCreateRequestDto cartEntryCreateRequestDto, Cart cart, Product product) {
        CartEntry cartEntry = CartEntryMapper.INSTANCE.toEntity(cartEntryCreateRequestDto);
        cartEntry.setPricePerPiece(product.getPrice());
        cartEntry.setTotalPriceEntry(product.getPrice().multiply(BigDecimal.valueOf(cartEntryCreateRequestDto.getQuantity())));
        if (cart.getCartEntries() == null) {
            cart.setCartEntries(new ArrayList<>());
        }
        cart.getCartEntries().add(cartEntry);
        cartEntryRepository.save(cartEntry);
    }

    private void updateExistingCartEntry(CartEntryCreateRequestDto cartEntryCreateRequestDto, CartEntry existingCartEntry, Product product) {
        int newQuantity = existingCartEntry.getQuantity() + cartEntryCreateRequestDto.getQuantity();
        if (product.getAvailableQuantity() < newQuantity) {
            throw new IllegalArgumentException("Not enough products in stock");
        }
        existingCartEntry.setQuantity(newQuantity);
        if (existingCartEntry.getPricePerPiece() == null) {
            existingCartEntry.setPricePerPiece(product.getPrice());
        }
        existingCartEntry.setTotalPriceEntry(existingCartEntry.getPricePerPiece().multiply(BigDecimal.valueOf(newQuantity)));
        cartEntryRepository.save(existingCartEntry);
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
    }
}