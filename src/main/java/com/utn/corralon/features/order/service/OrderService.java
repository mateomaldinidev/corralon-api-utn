package com.utn.corralon.features.order.service;

import com.utn.corralon.exception.BusinessRuleException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.repository.AddressRepository;
import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart.repository.CartRepository;
import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.enums.OrderStatus;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.order.mapper.OrderMapper;
import com.utn.corralon.features.order.repository.OrderRepository;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.orderItem.mapper.OrderItemMapper;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.utn.corralon.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;

    //CREATE
    @Override
    @Transactional
    public OrderResponseDTO createFromCart(
            CartEntity cart, UUID addressId
    ) {
        AddressEntity address = addressRepository.findByExternalId(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Address not found", addressId)
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

                            variant.setStock(
                                    variant.getStock() - cartItem.getQuantity()
                            );

                            productVariantRepository.save(variant);


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
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        OrderEntity savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDTO(savedOrder);
    }

    //GET ALL
    @Override
    public List<OrderAdminResponseDTO> getAll() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toAdminResponse)
                .toList();
    }

    //GET BY EXTERNAL ID
    @Override
    public OrderResponseDTO getByExternalId(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found",externalId));

        return orderMapper.toResponseDTO(order);
    }


    @Override
    public List<OrderSummaryDTO> getOrdersByUser(
            UUID userExternalId)
    {
        findUser(userExternalId);

        return orderRepository
                .findByUser_ExternalId(userExternalId)
                .stream()
                .map(orderMapper::toSummary)
                .toList();
    }

    @Override
    public OrderAdminResponseDTO getAdminOrder(
            UUID externalId) {

        OrderEntity order = findOrder(externalId);

        return orderMapper.toAdminResponse(order);
    }

    private OrderEntity findOrder(
            UUID externalId) {

        return orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found",externalId));
    }

    @Override
    @Transactional
    public void cancelOrder(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found: ",externalId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
             throw new BadRequestException("Order is already cancelled");
        }

        if(order.getStatus() == OrderStatus.PAID){
            throw new BusinessRuleException("Paid orders cannot be cancelled");
        }

        restoreStock(order);

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }


    private void restoreStock(OrderEntity order) {

        for (OrderItemEntity item : order.getItems()) {

            ProductVariantEntity variant = item.getProductVariant();

            variant.setStock(variant.getStock() + item.getQuantity());

            productVariantRepository.save(variant);
        }
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

    private UserEntity findUser(UUID externalId) {

        return userRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuar not found.",
                                externalId
                        ));
    }

}
