package dd.projects.demo.service;

import dd.projects.demo.domain.dto.Address.AddressCreateRequestDto;
import dd.projects.demo.domain.dto.Address.AddressResponseDto;
import dd.projects.demo.domain.dto.Order.OrderCreateRequestDto;
import dd.projects.demo.domain.dto.Order.OrderResponseDto;
import dd.projects.demo.domain.entitiy.*;
import dd.projects.demo.mappers.AddressMapper;
import dd.projects.demo.mappers.OrderMapper;
import dd.projects.demo.repository.*;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;
    Logger log = org.apache.logging.log4j.LogManager.getLogger(CartService.class);

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, AddressRepository addressRepository, ProductRepository productRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.emailService = emailService;
    }

    public OrderResponseDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderMapper.INSTANCE::toOrderResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponseDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return orderRepository.findByUser(user)
                .stream()
                .map(OrderMapper.INSTANCE::toOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto createOrder(OrderCreateRequestDto orderCreateRequestDto) {
        log.info("Creating order for user with id: " + orderCreateRequestDto.getUserId());
        User user = userRepository.findById(orderCreateRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        log.info("Creating order for cart with id: " + orderCreateRequestDto.getCartId());
        Cart cart = cartRepository.findById(orderCreateRequestDto.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Cart orderedCart = copyCart(cart);

        Cart savedOrderedCart = cartRepository.save(orderedCart);

        Address deliveryAddress = findOrCreateAddress(orderCreateRequestDto.getDeliveryAddress());
        Address invoiceAddress = findOrCreateAddress(orderCreateRequestDto.getInvoiceAddress());

        Order order = OrderMapper.INSTANCE.toEntity(orderCreateRequestDto);
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        order.setCart(savedOrderedCart);
        order.setDeliveryAddress(deliveryAddress);
        order.setInvoiceAddress(invoiceAddress);
        order.setTotalPrice(cart.getTotalPrice());

        if(user.getOrders() == null) {
            user.setOrders(new ArrayList<>());
        }

        user.getOrders().add(order);
        userRepository.save(user);
        reduceProductQuantity(cart);

        Order savedOrder = orderRepository.save(order);

        clearCart(cart);

        try {
            emailService.sendOrderConfirmationEmail(user, savedOrder, savedOrderedCart);
            log.info("Order confirmation email sent to user with id: " + user.getId() + user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email: " + e.getMessage());
        }
        return OrderMapper.INSTANCE.toOrderResponseDto(savedOrder);
    }

    private Address findOrCreateAddress(AddressCreateRequestDto addressDto) {
        return addressRepository.findByDetails(
                addressDto.getStreetLine(),
                addressDto.getCity(),
                addressDto.getPostalCode(),
                addressDto.getCounty(),
                addressDto.getCountry()
                ).orElseGet(() -> {
                    Address newAddress = new Address();
                    newAddress.setStreetLine(addressDto.getStreetLine());
                    newAddress.setCity(addressDto.getCity());
                    newAddress.setPostalCode(addressDto.getPostalCode());
                    newAddress.setCounty(addressDto.getCounty());
                    newAddress.setCountry(addressDto.getCountry());
                    return addressRepository.save(newAddress);
                });
    }

    private void clearCart(Cart cart) {
        cart.getCartEntries().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private void reduceProductQuantity(Cart cart) {
        cart.getCartEntries().forEach(cartEntry -> {
            if(cartEntry.getQuantity() > cartEntry.getProduct().getAvailableQuantity()) {
                throw new IllegalArgumentException("Not enough products in stock");
            }

            cartEntry.getProduct().setAvailableQuantity(cartEntry.getProduct().getAvailableQuantity() - cartEntry.getQuantity());
            productRepository.save(cartEntry.getProduct());
        });
    }

    private Cart copyCart(Cart cart) {
        Cart orderedCart = new Cart();

        orderedCart.setUser(cart.getUser());
        orderedCart.setTotalPrice(cart.getTotalPrice());

        List<CartEntry> cartEntries = new ArrayList<>();
        for (CartEntry entry : cart.getCartEntries()) {
            CartEntry copiedEntry = new CartEntry();
            copiedEntry.setCart(orderedCart);

            Product clonedProduct = new Product();
            clonedProduct.setId(entry.getProduct().getId());
            clonedProduct.setName(entry.getProduct().getName());

            copiedEntry.setProduct(clonedProduct);
            copiedEntry.setQuantity(entry.getQuantity());
            copiedEntry.setPricePerPiece(entry.getPricePerPiece());
            copiedEntry.setTotalPriceEntry(entry.getTotalPriceEntry());
            cartEntries.add(copiedEntry);
        }
        orderedCart.setCartEntries(cartEntries);

        return orderedCart;
    }

}

