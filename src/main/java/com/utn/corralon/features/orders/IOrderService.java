package com.utn.corralon.features.orders;

import com.utn.corralon.features.orders.dto.OrderRequestDTO;
import com.utn.corralon.features.orders.dto.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    OrderResponseDTO create(OrderRequestDTO request);

    List<OrderResponseDTO> findAll();

    OrderResponseDTO findByExternalId(UUID externalId);

    OrderResponseDTO update(UUID externalId, OrderRequestDTO request);

    void delete(UUID externalId);
}
