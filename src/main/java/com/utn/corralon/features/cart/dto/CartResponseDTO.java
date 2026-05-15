package com.utn.corralon.features.cart.dto;

import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CartResponseDTO(
        UUID externalId,
        UUID userExternalId,
        LocalDateTime lastUpdated,
        List<CartItemResponseDTO> cartItems
) { }