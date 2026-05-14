package com.utn.corralon.features.orders.mapper;


import com.utn.corralon.features.address.addressEntity;
import com.utn.corralon.features.orders.dto.OrderRequestDTO;
import com.utn.corralon.features.orders.dto.OrderResponseDTO;
import com.utn.corralon.features.orders.orderEntity;
import com.utn.corralon.features.users.userEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public orderEntity toEntity(
            OrderRequestDTO request,
            userEntity user,
            addressEntity address
    ) {

        return orderEntity.builder()
                .user(user)
                .address(address)
                .total(request.getTotal())
                .created_at(LocalDateTime.now())
                .active(request.getActive())
                .build();
    }

    public OrderResponseDTO toResponse(orderEntity order) {

        return OrderResponseDTO.builder()
                .externalId(order.getExternalId())
                .userExternalId(order.getUser().getExternalId())
                .addressExternalId(order.getAddress().getExternalId())
                .total(order.getTotal())
                .created_at(order.getCreated_at())
                .active(order.getActive())
                .build();
    }
}
