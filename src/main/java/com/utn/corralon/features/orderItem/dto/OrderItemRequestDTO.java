package com.utn.corralon.features.orderItem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderItemRequestDTO {

    @NotNull
    private UUID productVariantExternalId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
