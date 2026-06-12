package com.utn.corralon.features.productVariant.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantRequestDTO {
    @NotBlank(message = "Attribute is required")
    @Size(max = 255, message = "Attribute must be less than 255 characters")
    private String attribute;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @Positive(message = "Wholesale price must be positive")
    private BigDecimal wholesalePrice;

    @Min(value = 1, message = "Wholesale minimum quantity must be at least 1")
    private Integer wholesaleMinQty;

    @NotNull(message = "Product is required")
    private UUID productId;

}
