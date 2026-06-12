package com.utn.corralon.features.orderItem.mapper;


import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.orderItem.dto.OrderItemRequestDTO;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component

public class OrderItemMapper {
    private final ModelMapper modelMapper;

    public OrderItemResponseDTO toResponseDTO(OrderItemEntity entity) {

        OrderItemResponseDTO dto =
                modelMapper.map(entity, OrderItemResponseDTO.class);

        dto.setOrderExternalId(
                entity.getOrder().getExternalId()
        );

        dto.setProductVariantExternalId(
                entity.getProductVariant().getExternalId()
        );

        return dto;
    }

    public OrderItemEntity toEntity(
            OrderEntity order,
            ProductVariantEntity variant,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {

        return OrderItemEntity.builder()
                .order(order)
                .productVariant(variant)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();
    }
}

