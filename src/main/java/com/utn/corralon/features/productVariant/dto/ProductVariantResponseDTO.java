package com.utn.corralon.features.productVariant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantResponseDTO {
    @NotBlank
    private UUID externalId;
    @NotBlank
    private String attribute;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private Integer stock;
    @NotBlank
    private Boolean active;
    @NotBlank
    private BigDecimal wholesalePrice;
    @NotBlank
    private BigDecimal wholeMinStock;
    @NotBlank
    private UUID productId;
    @NotBlank
    private String productDescription;
}


