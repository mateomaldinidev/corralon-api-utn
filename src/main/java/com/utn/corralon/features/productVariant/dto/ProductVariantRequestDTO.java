package com.utn.corralon.features.productVariant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotBlank(message = "Stock is required")
    @Positive(message = "Stock must be positive")
    private Integer stock;

    @NotBlank(message = "Active is required")
    private Boolean active;

    @NotBlank(message = "Wholesale price is required")
    @Positive(message = "Wholesale price must be positive")
    private BigDecimal wholesalePrice;

    @NotBlank(message = "Whole min stock is required")
    @Positive(message = "Whole min stock must be positive")
    private BigDecimal wholeMinStock;

    @NotBlank(message = "Product is required")
    private UUID productId;

}
