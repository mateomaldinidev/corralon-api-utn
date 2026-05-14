package com.utn.corralon.features.productVariant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class productVariantRequestDTO {

    @NotBlank
    private String attribute;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    @NotNull
    private Boolean active;

    @NotNull
    @PositiveOrZero
    private BigDecimal wholesalePrice;

    @NotNull
    @PositiveOrZero
    private BigDecimal wholeMinStock;

    @NotNull
    private UUID productExternalId;
}