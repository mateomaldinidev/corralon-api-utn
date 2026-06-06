package com.utn.corralon.features.order.mapper;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.order.dto.CreateOrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.orderItem.mapper.OrderItemMapper;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;


    public OrderResponseDTO toResponse(OrderEntity order) {

        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);

        dto.setAddressExternalId(order.getAddress().getExternalId());

        return dto;
    }

    public OrderAdminResponseDTO toAdminResponse(OrderEntity order) {

        OrderAdminResponseDTO dto = modelMapper.map(order, OrderAdminResponseDTO.class);

        dto.setUserExternalId(order.getUser().getExternalId());

        dto.setAddressExternalId(order.getAddress().getExternalId());

        return dto;
    }

    public OrderSummaryDTO toSummary(OrderEntity order) {

        return modelMapper.map(order, OrderSummaryDTO.class);
    }
}

