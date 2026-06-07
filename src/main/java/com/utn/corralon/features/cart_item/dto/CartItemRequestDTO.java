package com.utn.corralon.features.cart_item.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequestDTO {
    @NotNull(message = "Product variant ID cannot be null")
    private UUID productVariantId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Positive(message = "Quantity must be positive.")
    private Integer quantity;


}
