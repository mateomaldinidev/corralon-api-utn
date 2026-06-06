package com.utn.corralon.features.order.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.repository.AddressRepository;
import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart.repository.CartRepository;
import com.utn.corralon.features.cart_item.entity.CartItemEntity;
import com.utn.corralon.features.order.dto.CreateOrderRequestDTO;
import com.utn.corralon.features.order.dto.OrderAdminResponseDTO;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.dto.OrderSummaryDTO;
import com.utn.corralon.features.order.entity.OrderEntity;
import com.utn.corralon.features.order.mapper.OrderMapper;
import com.utn.corralon.features.order.orderEnum.OrderStatus;
import com.utn.corralon.features.order.repository.OrderRepository;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
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
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request, UUID userExternalId) {

        UserEntity user = findUser(userExternalId);

        AddressEntity address =
                findAddress(request.getAddressId());

        validateAddressOwnership(user, address);

        CartEntity cart = findCart(user);

        validateCart(cart);

        validateStock(cart);

        OrderEntity order = OrderEntity.builder()
                .user(user)
                .address(address)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItemEntity cartItem : cart.getCartItems()) {
            OrderItemEntity orderItem = buildOrderItem(cartItem);
            order.addItem(orderItem);
            total = total.add(orderItem.getSubtotal());
            discountStock(cartItem);
        }

        order.setTotal(total);

        OrderEntity savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderAdminResponseDTO> getAll() {

        return orderRepository.findAll().stream()
                .map(orderMapper::toAdminResponse)
                .toList();
    }

    @Override
    public OrderResponseDTO getByExternalId(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordren no encontrada"));

        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderSummaryDTO> getOrdersByUser(
            UUID userExternalId) {

        findUser(userExternalId);

        return orderRepository
                .findByUserExternalId(userExternalId)
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
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));
    }



    @Override
    @Transactional
    public void cancelOrder(UUID externalId) {

        OrderEntity order = orderRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Orden no encontrada"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
             throw new BadRequestException("La orden ya fue cancelada");
        }

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED) {

            throw new BadRequestException("La orden no puede cancelarse");
        }

        restoreStock(order);

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    private void restoreStock(OrderEntity order) {

        for (OrderItemEntity item : order.getItems()) {

            ProductVariantEntity variant = item.getProductVariant();

            variant.setStock(variant.getStock() + item.getQuantity());
        }
    }

    private OrderItemEntity buildOrderItem(
            CartItemEntity cartItem) {

        ProductVariantEntity variant =
                cartItem.getProductVariant();

        BigDecimal unitPrice =
                variant.getPrice();

        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return OrderItemEntity.builder()
                .productVariant(variant)
                .quantity(cartItem.getQuantity())
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();
    }

    private void discountStock(
            CartItemEntity cartItem) {

        ProductVariantEntity variant = cartItem.getProductVariant();

        variant.setStock(variant.getStock() - cartItem.getQuantity());
    }
    private UserEntity findUser(UUID externalId) {

        return userRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private AddressEntity findAddress(
            UUID externalId) {

        return addressRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada"));
    }

    private CartEntity findCart(UserEntity user) {

        return cartRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    }


    private void validateCart(CartEntity cart) {

        if (cart.getCartItems() == null ||
                cart.getCartItems().isEmpty()) {

           throw new BadRequestException("El carrito está vacío");
        }
    }

    private void validateAddressOwnership(
            UserEntity user,
            AddressEntity address) {

        if (!address.getUser().getId().equals(user.getId())) {

           throw new BadRequestException("La dirección no pertenece al usuario");
        }
    }

    private void validateStock(CartEntity cart) {

        for (CartItemEntity item : cart.getCartItems()) {

            ProductVariantEntity variant = item.getProductVariant();

            if (variant.getStock() < item.getQuantity()) {

                throw new BadRequestException("Stock insuficiente para " + variant.getProduct().getName());
            }
        }
    }

}
