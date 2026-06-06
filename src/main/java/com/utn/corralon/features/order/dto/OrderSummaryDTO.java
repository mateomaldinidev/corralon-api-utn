package com.utn.corralon.features.order.dto;

import com.utn.corralon.features.order.orderEnum.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrderSummaryDTO {

    private UUID externalId;

    private BigDecimal total;

    private OrderStatus status;

    private LocalDateTime createdAt;
}
