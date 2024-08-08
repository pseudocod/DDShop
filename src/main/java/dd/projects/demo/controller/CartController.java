package dd.projects.demo.controller;

import dd.projects.demo.domain.dto.Cart.CartResponseDto;
import dd.projects.demo.domain.dto.CartEntry.CartEntryCreateRequestDto;
import dd.projects.demo.domain.dto.CartEntry.CartEntryEditDto;
import dd.projects.demo.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Long id) {
        try {
            CartResponseDto cart = cartService.getCartById(id);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cartId}/add-to-cart")
    public ResponseEntity<CartResponseDto> addToCart(@PathVariable Long cartId, @RequestBody CartEntryCreateRequestDto cartEntryCreateRequestDto) {
        try {
            CartResponseDto cart = cartService.addCartEntry(cartId, cartEntryCreateRequestDto);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            logger.error("Error adding to cart: cartId={}, productId={}, quantity={}", cartId, cartEntryCreateRequestDto.getProductId(), cartEntryCreateRequestDto.getQuantity(), e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{cartId}/update-cart-entry-quantity")
    public ResponseEntity<CartResponseDto> updateCartEntryQuantity(@PathVariable Long cartId, @RequestBody CartEntryEditDto cartEntryEditDto) {
        try {
            CartResponseDto cart = cartService.updateCartEntryQuantity(cartId, cartEntryEditDto);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            logger.error("Error updating cart entry quantity: cartId={}, cartEntryId={}, quantity={}", cartId, cartEntryEditDto.getCartEntryId(), cartEntryEditDto.getQuantity(), e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}/delete-cart-entry/{cartEntryId}")
    public ResponseEntity<CartResponseDto> deleteCartEntry(@PathVariable Long cartId, @PathVariable Long cartEntryId) {
        try {
            CartResponseDto cart = cartService.deleteCartEntry(cartId, cartEntryId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            logger.error("Error deleting cart entry: cartId={}, cartEntryId={}", cartId, cartEntryId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}