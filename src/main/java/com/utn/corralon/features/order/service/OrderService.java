package com.utn.corralon.features.order.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.repository.AddressRepository;
import com.utn.corralon.features.order.dto.OrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.order.mapper.OrderMapper;
import com.utn.corralon.features.order.repository.OrderRepository;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.orderItem.mapper.OrderItemMapper;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public OrderResponseDTO create(OrderRequestDTO dto) {

        UserEntity user = userRepository
                .findByExternalId(dto.getUserExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found", userId)
                );

        AddressEntity address = addressRepository
                .findByExternalId(dto.getAddressExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found", userId)
                );

        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setAddress(address);
        order.setActive(dto.getActive());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItemEntity> items = dto.getItems().stream()
                .map(itemDto -> {

                    ProductVariantEntity variant =
                            productVariantRepository
                                    .findByExternalId(
                                            itemDto.getProductVariantExternalId()
                                    )
                                    .orElseThrow(() ->
                                            new ResourceNotFoundException(
                                                    "Product variant not found",
                                                    userId)
                                    );

                    return orderItemMapper.toEntity(
                            itemDto,
                            order,
                            variant
                    );
                })
                .toList();

        order.setItems(items);

        BigDecimal total = items.stream()
                .map(item ->
                        item.getUnitPrice()
                                .multiply(
                                        BigDecimal.valueOf(item.getQuantity())
                                )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);

        OrderEntity savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAll() {

        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO getByExternalId(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found", userId)
                );

        return orderMapper.toResponseDTO(order);
    }

    @Override
    public OrderResponseDTO update(
            UUID externalId,
            OrderRequestDTO dto
    ) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found", userId)
                );

        UserEntity user = userRepository
                .findByExternalId(dto.getUserExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found", userId)
                );

        AddressEntity address = addressRepository
                .findByExternalId(dto.getAddressExternalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found", userId)
                );

        order.setUser(user);
        order.setAddress(address);
        order.setActive(dto.getActive());

        List<OrderItemEntity> items = dto.getItems().stream()
                .map(itemDto -> {

                    ProductVariantEntity variant =
                            productVariantRepository
                                    .findByExternalId(
                                            itemDto.getProductVariantExternalId()
                                    )
                                    .orElseThrow(() ->
                                            new ResourceNotFoundException(
                                                    "Product variant not found",
                                                    userId)
                                    );

                    return orderItemMapper.toEntity(
                            itemDto,
                            order,
                            variant
                    );
                })
                .toList();

        order.getItems().clear();
        order.getItems().addAll(items);

        BigDecimal total = items.stream()
                .map(item ->
                        item.getUnitPrice()
                                .multiply(
                                        BigDecimal.valueOf(item.getQuantity())
                                )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);

        OrderEntity updatedOrder = orderRepository.save(order);

        return orderMapper.toResponseDTO(updatedOrder);
    }

    @Override
    public void delete(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found", userId)
                );

        orderRepository.delete(order);
    }
}
