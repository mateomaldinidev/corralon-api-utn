package com.utn.corralon.features.orderItem.mapper;


import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class OrderItemMapper {
    private final ModelMapper modelMapper;

    public OrderItemResponseDTO toResponseDTO(OrderItemEntity entity) {

        OrderItemResponseDTO dto = new OrderItemResponseDTO();

        dto.setExternalId(entity.getExternalId());
        dto.setProductVariantExternalId(entity.getProductVariant().getExternalId());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setSubtotal(entity.getSubtotal());

        return dto;
    }
}

