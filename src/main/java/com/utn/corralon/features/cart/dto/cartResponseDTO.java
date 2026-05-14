package com.utn.corralon.features.cart.dto;

import com.utn.corralon.features.cart_item.dto.cartItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record cartResponseDTO(
        UUID externalId,
        UUID userExternalId,
        LocalDateTime lastUpdated,
        List<cartItemResponseDTO> cartItems
) { }