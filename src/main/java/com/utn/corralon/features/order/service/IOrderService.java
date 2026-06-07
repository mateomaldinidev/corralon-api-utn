package com.utn.corralon.features.order.service;

import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.order.dto.OrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.stockMovement.enums.StockMovementType;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    OrderResponseDTO createFromCart(
            CartEntity cart, UUID addressId
    );

    List<OrderResponseDTO> getAll();

    OrderResponseDTO getByExternalId(UUID externalId);

    void delete(UUID externalId);

}
