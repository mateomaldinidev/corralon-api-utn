package com.utn.corralon.features.order.dto;

import com.utn.corralon.features.order.enums.DeliveryType;
import com.utn.corralon.features.order.enums.OrderStatus;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderAdminResponseDTO {

    private UUID externalId;
    private UUID userExternalId;
    private String customerName;
    private UUID addressExternalId;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private DeliveryType deliveryType;
    private List<OrderItemResponseDTO> items;
}
