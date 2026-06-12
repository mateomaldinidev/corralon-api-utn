package com.utn.corralon.features.cart_item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDTO {
    private UUID externalId;
    private UUID cartExternalId;
    private UUID productVariantExternalId;

    private String productName; // <-- Nuevo
    private String variantAttribute; // <-- Nuevo
    private BigDecimal unitPrice; // <-- Nuevo

    private Integer quantity;
    private LocalDateTime createdAt;
}
