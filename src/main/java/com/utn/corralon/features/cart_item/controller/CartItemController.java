package com.utn.corralon.features.cart_item.controller;

import com.utn.corralon.features.cart_item.dto.CartItemQuantityUpdateDTO;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import com.utn.corralon.features.cart_item.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<CartItemResponseDTO> getCartItemByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(cartItemService.getCartItemByExternalId(externalId));
    }

    @PatchMapping("/{externalId}/quantity")
    public ResponseEntity<CartItemResponseDTO> updateCartItemQuantity(
            @PathVariable UUID externalId,
            @Valid @RequestBody CartItemQuantityUpdateDTO updateDTO) {
        return ResponseEntity.ok(cartItemService.updateCartItemQuantity(externalId, updateDTO.getQuantity()));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID externalId) {
        cartItemService.deleteCartItem(externalId);
        return ResponseEntity.noContent().build();
    }
}