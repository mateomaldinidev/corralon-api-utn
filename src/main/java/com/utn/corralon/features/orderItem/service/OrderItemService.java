package com.utn.corralon.features.orderItem.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.order.repository.OrderRepository;
import com.utn.corralon.features.orderItem.dto.OrderItemRequestDTO;
import com.utn.corralon.features.orderItem.dto.OrderItemResponseDTO;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.orderItem.mapper.OrderItemMapper;
import com.utn.corralon.features.orderItem.repository.OrderItemRepository;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public OrderItemResponseDTO create(OrderItemRequestDTO dto) {

        OrderEntity order = orderRepository
                .findByExternalId(dto.getOrderExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        ProductVariantEntity variant =
                productVariantRepository
                        .findByExternalId(
                                dto.getProductVariantExternalId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product variant not found"
                                )
                        );

        OrderItemEntity entity = orderItemMapper.toEntity(
                dto,
                order,
                variant
        );

        OrderItemEntity savedEntity =
                orderItemRepository.save(entity);

        return orderItemMapper.toResponseDTO(savedEntity);
    }

    @Override
    public List<OrderItemResponseDTO> getAll() {

        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toResponseDTO)
                .toList();
    }

    @Override
    public OrderItemResponseDTO getByExternalId(
            UUID externalId
    ) {

        OrderItemEntity entity = orderItemRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order item not found"
                        )
                );

        return orderItemMapper.toResponseDTO(entity);
    }

    @Override
    public OrderItemResponseDTO update(
            UUID externalId,
            OrderItemRequestDTO dto
    ) {

        OrderItemEntity entity = orderItemRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order item not found"
                        )
                );

        OrderEntity order = orderRepository
                .findByExternalId(dto.getOrderExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        ProductVariantEntity variant =
                productVariantRepository
                        .findByExternalId(
                                dto.getProductVariantExternalId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product variant not found"
                                )
                        );

        entity.setOrder(order);
        entity.setProductVariant(variant);
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());

        OrderItemEntity updatedEntity =
                orderItemRepository.save(entity);

        return orderItemMapper.toResponseDTO(updatedEntity);
    }

    @Override
    public void delete(UUID externalId) {

        OrderItemEntity entity = orderItemRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order item not found"
                        )
                );

        orderItemRepository.delete(entity);
    }
}
