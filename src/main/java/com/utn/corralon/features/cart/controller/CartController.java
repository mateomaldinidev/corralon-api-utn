package com.utn.corralon.features.cart.controller;

import com.utn.corralon.features.cart.dto.CartRequestDTO;
import com.utn.corralon.features.cart.dto.CartResponseDTO;
import com.utn.corralon.features.cart.service.ICartService;
import com.utn.corralon.features.cart_item.dto.CartItemQuantityUpdateDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.enums.DeliveryType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CART_CREATE_OR_UPDATE')")
    public ResponseEntity<CartResponseDTO> createOrUpdateCart(@Valid @RequestBody CartRequestDTO cartRequest) {

        return ResponseEntity.ok( cartService.createOrUpdateCart(cartRequest));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('CART_READ')")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PatchMapping("/{userId}/items/{productVariantId}")
    @PreAuthorize("hasAuthority('CART_UPDATE_ITEM_QUANTITY')")
    public ResponseEntity<CartResponseDTO> updateCartItemQuantity(
            @PathVariable UUID userId,
            @PathVariable UUID productVariantId,
            @Valid @RequestBody CartItemQuantityUpdateDTO updateDTO) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(userId, productVariantId, updateDTO));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('CART_CLEAR')")
    public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/checkout")
    @PreAuthorize("hasAuthority('CART_CHECKOUT')")
    public ResponseEntity<OrderResponseDTO> checkout(
            @PathVariable UUID userId,
            @RequestParam DeliveryType deliveryType,
            @RequestParam(required = false) UUID addressId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.checkout(userId, addressId, deliveryType));
    }
}
