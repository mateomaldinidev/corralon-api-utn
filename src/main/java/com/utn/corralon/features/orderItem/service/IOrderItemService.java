package com.utn.corralon.features.orderItem.service;

import com.utn.corralon.features.orderItem.dto.OrderItemRequestDTO;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IOrderItemService {

    OrderItemResponseDTO create(OrderItemRequestDTO dto);

    List<OrderItemResponseDTO> getAll();

    OrderItemResponseDTO getByExternalId(UUID externalId);

    OrderItemResponseDTO update(
            UUID externalId,
            OrderItemRequestDTO dto
    );

    void delete(UUID externalId);
}
