package com.utn.corralon.features.order.mapper;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.order.OrderStatus;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.order.dto.OrderRequestDTO;
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
    private final OrderItemMapper orderItemMapper;


    public OrderEntity toEntity(UserEntity user, AddressEntity address)
    {

        OrderEntity order =
                OrderEntity.builder()
                        .user(user)
                        .address(address)
                        .createdAt(LocalDateTime.now())
                        .status(OrderStatus.PENDING_PAYMENT)
                        .build();

        return order;
    }


    public OrderResponseDTO toResponseDTO(OrderEntity orderEntity) {

        OrderResponseDTO dto = modelMapper.map(orderEntity, OrderResponseDTO.class);

        dto.setUserExternalId(orderEntity.getUser().getExternalId());
        dto.setAddressExternalId(orderEntity.getAddress().getExternalId());

        dto.setItems(
                orderEntity.getItems()
                        .stream()
                        .map(orderItemMapper::toResponseDTO)
                        .toList()
        );

        return dto;
    }

}
