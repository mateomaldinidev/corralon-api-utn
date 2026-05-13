package com.utn.corralon.features.productVariant.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductVariantResponseDTO {

    private UUID externalId;

    private String attribute;

    private BigDecimal price;

    private Integer stock;

    private Boolean active;

    private BigDecimal wholesalePrice;

    private BigDecimal wholeMinStock;

    private UUID productExternalId;

    private String productDescription;
}
