package com.utn.corralon.features.orders.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    @NotNull
    private UUID userExternalId;

    @NotNull
    private UUID addressExternalId;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal total;

    @NotNull
    private Boolean active;
}
