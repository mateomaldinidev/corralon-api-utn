package com.utn.corralon.features.order.dto;

import com.utn.corralon.features.order.OrderStatus;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponseDTO {
    private UUID externalId;

    private UUID userExternalId;

    private UUID addressExternalId;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private OrderStatus status;

    private List<OrderItemResponseDTO> items;
}
