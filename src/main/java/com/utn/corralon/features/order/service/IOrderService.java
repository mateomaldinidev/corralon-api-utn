package com.utn.corralon.features.order.service;

import com.utn.corralon.features.order.dto.OrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    OrderResponseDTO create(OrderRequestDTO dto);

    List<OrderResponseDTO> getAll();

    OrderResponseDTO getByExternalId(UUID externalId);

    OrderResponseDTO update(UUID externalId, OrderRequestDTO dto);

    void delete(UUID externalId);
}
