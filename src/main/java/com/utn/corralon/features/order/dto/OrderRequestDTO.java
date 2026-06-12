package com.utn.corralon.features.order.dto;


import com.utn.corralon.features.order.enums.DeliveryType;
import com.utn.corralon.features.orderItem.dto.OrderItemRequestDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    @NotNull
    private UUID userExternalId;

    @NotNull
    private DeliveryType deliveryType;

    // opcional según deliveryType
    private UUID addressExternalId;
}

