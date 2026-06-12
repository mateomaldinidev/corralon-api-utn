package com.utn.corralon.features.cart.controller;

import com.utn.corralon.features.cart.dto.CartRequestDTO;
import com.utn.corralon.features.cart.dto.CartResponseDTO;
import com.utn.corralon.features.cart.service.CartService;
import com.utn.corralon.features.cart_item.dto.CartItemQuantityUpdateDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAll() { 
        return ResponseEntity.ok(cartService.getAll());
    }
    @PostMapping
    public ResponseEntity<CartResponseDTO> createOrUpdateCart(@Valid @RequestBody CartRequestDTO cartRequest) {

        return ResponseEntity.ok( cartService.createOrUpdateCart(cartRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    //  actualizar la cantidad de un ítem especifico del carrito
    @PatchMapping("/{userId}/items/{productVariantId}")
    public ResponseEntity<CartResponseDTO> updateCartItemQuantity(
            @PathVariable UUID userId,
            @PathVariable UUID productVariantId,
            @Valid @RequestBody CartItemQuantityUpdateDTO updateDTO) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(userId, productVariantId, updateDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build(); // TIRA UN 204
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(
            @PathVariable UUID userId,
            @RequestParam(required = false) UUID addressId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.checkout(userId, addressId));
    }
}