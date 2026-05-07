package com.utn.corralon.features.productVariant.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductVariantResponseDTO(
    UUID externalId,
    String atrivute,
    BigDecimal price,
    Integer stock,
    Boolean active,
    BigDecimal wholesalePrice,
    BigDecimal wholeMinStock

) { }
