package com.utn.corralon.features.orders;

import com.utn.corralon.features.addresses.addressEntity;
import com.utn.corralon.features.addresses.AddressRepository;
import com.utn.corralon.features.orders.dto.OrderRequestDTO;
import com.utn.corralon.features.orders.dto.OrderResponseDTO;
import com.utn.corralon.features.orders.mapper.OrderMapper;
import com.utn.corralon.features.users.UserRepository;
import com.utn.corralon.features.users.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {

        userEntity user = userRepository
                .findByExternalId(request.getUserExternalId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        addressEntity address = addressRepository
                .findByExternalId(request.getAddressExternalId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        orderEntity order = orderMapper.toEntity(
                request,
                user,
                address
        );

        return orderMapper.toResponse(
                orderRepository.save(order)
        );
    }

    @Override
    public List<OrderResponseDTO> findAll() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponseDTO findByExternalId(UUID externalId) {

        orderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponseDTO update(
            UUID externalId,
            OrderRequestDTO request
    ) {

        orderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        userEntity user = userRepository
                .findByExternalId(request.getUserExternalId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        addressEntity address = addressRepository
                .findByExternalId(request.getAddressExternalId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        order.setUser(user);
        order.setAddress(address);
        order.setTotal(request.getTotal());
        order.setActive(request.getActive());

        return orderMapper.toResponse(
                orderRepository.save(order)
        );
    }

    @Override
    public void delete(UUID externalId) {

        orderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }
}