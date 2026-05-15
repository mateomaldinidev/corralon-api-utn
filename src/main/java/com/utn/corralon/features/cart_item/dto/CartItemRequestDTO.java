package com.utn.corralon.features.cart_item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequestDTO {
    @NotNull
    private UUID cartId;
    @NotNull
    private UUID productVariantId;
    @NotNull
    private Integer quantity;


}
