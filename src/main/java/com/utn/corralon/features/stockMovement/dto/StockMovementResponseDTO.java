package com.utn.corralon.features.stockMovement.dto;

import com.utn.corralon.features.stockMovement.enums.StockMovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockMovementResponseDTO(
    UUID externalId,
    StockMovementType type,
    Integer quantity,
    LocalDateTime movementDate,
    String reason,
    UUID variantExternalId
) {}
