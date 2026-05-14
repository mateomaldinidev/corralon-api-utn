package com.utn.corralon.features.cart_item.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record cartItemResponseDTO(
        UUID externalId,
        UUID cartExternalId,
        UUID productVariantExternalId,
        Integer quantity,
        LocalDateTime createdAt
) { }