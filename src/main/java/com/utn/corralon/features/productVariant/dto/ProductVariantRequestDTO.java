package com.utn.corralon.features.productVariant.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ProductVariantRequestDTO {
    @NotBlank
    private String atrivute;

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


}
