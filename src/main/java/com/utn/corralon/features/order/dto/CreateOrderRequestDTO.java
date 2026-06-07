package com.utn.corralon.features.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
public class CreateOrderRequestDTO {

    @NotNull(message = "La dirección es obligatoria")
    private UUID addressId;
}