package com.utn.corralon.features.order.dto;


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
    private UUID addressExternalId;

    @NotNull
    private BigDecimal total;

    @NotNull
    private Boolean active;

    @NotEmpty
    private List<OrderItemRequestDTO> items;
}
