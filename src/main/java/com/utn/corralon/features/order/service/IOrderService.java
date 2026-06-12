package com.utn.corralon.features.order.service;

import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.order.enums.DeliveryType;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    OrderResponseDTO createFromCart(
            CartEntity cart, UUID addressId,
             DeliveryType deliveryType  // null = retiro en sucursal
    );

    List<OrderAdminResponseDTO> getAll();

    OrderResponseDTO getByExternalId(UUID externalId);

    List<OrderSummaryDTO> getOrdersByUser(UUID userExternalId);

    OrderAdminResponseDTO getAdminOrder(UUID externalId);

    void cancelOrder(UUID externalId, UUID userExternalId);
}
