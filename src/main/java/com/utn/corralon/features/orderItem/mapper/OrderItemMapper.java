package com.utn.corralon.features.orderItem.mapper;


import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.orderItem.dto.OrderItemRequestDTO;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {
    private final ModelMapper modelMapper;

    public OrderItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderItemResponseDTO toResponseDTO(OrderItemEntity entity) {

        OrderItemResponseDTO dto =
                modelMapper.map(entity, OrderItemResponseDTO.class);

        dto.setOrderExternalId(
                entity.getOrder().getExternalId()
        );

        dto.setProductVariantExternalId(
                entity.getProductVariant().getExternalId()
        );

        dto.setSubtotal(
                entity.getUnitPrice()
                        .multiply(BigDecimal.valueOf(entity.getQuantity()))
        );

        return dto;
    }

    public OrderItemEntity toEntity(
            OrderItemRequestDTO dto,
            OrderEntity order,
            ProductVariantEntity variant
    ) {

        OrderItemEntity entity = new OrderItemEntity();

        entity.setOrder(order);
        entity.setProductVariant(variant);
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());

        return entity;
    }
}

