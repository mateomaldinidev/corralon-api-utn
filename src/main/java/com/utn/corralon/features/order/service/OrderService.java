package com.utn.corralon.features.order.service;

import com.utn.corralon.exception.BusinessRuleException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.repository.AddressRepository;
import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.order.OrderStatus;
import com.utn.corralon.features.order.dto.OrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.order.mapper.OrderMapper;
import com.utn.corralon.features.order.repository.OrderRepository;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.orderItem.mapper.OrderItemMapper;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.stockMovement.entity.StockMovementEntity;
import com.utn.corralon.features.stockMovement.enums.StockMovementType;
import com.utn.corralon.features.stockMovement.repository.StockMovementRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    private final AddressRepository addressRepository;

    //CREATE
    @Override
    @Transactional
    public OrderResponseDTO createFromCart(
            CartEntity cart, UUID addressId
    ) {
        AddressEntity address = addressRepository.findByExternalId(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Address not found", userId)
                        );

        if (!address.getUser().getExternalId()
                .equals(cart.getUser().getExternalId())) {
            throw new BusinessRuleException("Address does not belong to user");
        }

        if (cart.getCartItems().isEmpty()) {
            throw new BusinessRuleException("Cart is empty");
        }

        OrderEntity order = orderMapper.toEntity( cart.getUser(), address);

        List<OrderItemEntity> items =
                cart.getCartItems()
                        .stream()
                        .map(cartItem  -> {

                            ProductVariantEntity variant =
                                    cartItem.getProductVariant();

                            if (!variant.getActive()) {
                                throw new BusinessRuleException("Product variant is inactive");
                            }

                            if (variant.getStock() < cartItem.getQuantity())
                            {
                                throw new BusinessRuleException("Insufficient stock");
                            }

                            BigDecimal unitPrice = calculateUnitPrice(variant,
                                    cartItem.getQuantity());

                            BigDecimal subtotal =
                                    unitPrice.multiply(
                                            BigDecimal.valueOf(
                                                    cartItem.getQuantity())
                                    );


                            return orderItemMapper.toEntity(
                                    order,
                                    variant,
                                    cartItem.getQuantity(),
                                    unitPrice,
                                    subtotal
                            );
                        })
                        .toList();

        BigDecimal total = items
                .stream()
                .map(OrderItemEntity::getSubtotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        order.setItems(items);
        order.setTotal(total);

        OrderEntity savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(savedOrder);
    }

    //GET ALL
    @Override
    public List<OrderResponseDTO> getAll() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponseDTO)
                .toList();
    }

    //GET BY EXTERNAL ID
    @Override
    public OrderResponseDTO getByExternalId(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found", userId)
                );

        return orderMapper.toResponseDTO(order);
    }

    //GET DELETE
    @Override
    @Transactional
    public void delete(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found", userId)
                );

        if (order.getStatus().equals(OrderStatus.CANCELLED)) {
            throw new BusinessRuleException(
                    "Order is already cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    private BigDecimal calculateUnitPrice(
            ProductVariantEntity variant,
            Integer quantity
    ) {

        if (variant.getWholesaleMinQty() != null && quantity >= variant.getWholesaleMinQty())
        {
            return variant.getWholesalePrice();
        }

        return variant.getPrice();
    }



}
