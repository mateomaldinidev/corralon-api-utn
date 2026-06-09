package com.utn.corralon.features.cart.service;

import com.utn.corralon.exception.BusinessRuleException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.exception.StockInsufficientException;
import com.utn.corralon.features.cart.dto.CartRequestDTO;
import com.utn.corralon.features.cart.dto.CartResponseDTO;
import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart.repository.CartRepository;
import com.utn.corralon.features.cart_item.dto.CartItemRequestDTO;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import com.utn.corralon.features.cart_item.dto.CartItemQuantityUpdateDTO;
import com.utn.corralon.features.cart_item.entity.CartItemEntity;
import com.utn.corralon.features.order.dto.OrderResponseDTO;
import com.utn.corralon.features.order.service.OrderService;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;

@AllArgsConstructor

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @Transactional
    public CartResponseDTO createOrUpdateCart(CartRequestDTO cartRequest) {
        UserEntity user = getActiveUser(cartRequest.getUserId());

        CartEntity cart = cartRepository.findByUser(user)
                .orElseGet(() -> CartEntity.builder()
                        .user(user)
                        .cartItems(new ArrayList<>())
                        .build());

        // Mapear los ítems existentes del carrito para una búsqueda eficiente
        Map<UUID, CartItemEntity> existingItemsMap = cart.getCartItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getProductVariant().getExternalId(),
                        item -> item
                ));

        // Mapear los ítems entrantes de la solicitud para una búsqueda eficiente
        Map<UUID, CartItemRequestDTO> incomingItemsMap = cartRequest.getItems().stream()
                .collect(Collectors.toMap(
                        CartItemRequestDTO::getProductVariantId,
                        item -> item
                ));

        // Lista para almacenar los ítems que deben ser eliminados del carrito
        List<CartItemEntity> itemsToRemove = new ArrayList<>();

        // 1. Procesar ítems existentes: actualizar o marcar para eliminación
        for (CartItemEntity existingItem : new ArrayList<>(cart.getCartItems())) {
            UUID productVariantId = existingItem.getProductVariant().getExternalId();
            CartItemRequestDTO incomingItemRequest = incomingItemsMap.get(productVariantId);

            if (incomingItemRequest != null) {
                ProductVariantEntity productVariant = productVariantRepository.findByExternalId(productVariantId)
                        .orElseThrow(() -> new ResourceNotFoundException("ProductVariant not found with ID: ", productVariantId));

                // Validar si el producto está activo
                if (!productVariant.getActive()) {
                    throw new BusinessRuleException("Product variant with ID " + productVariantId + " is not active and cannot be added to cart.");
                }

                if (productVariant.getStock() < incomingItemRequest.getQuantity()) {
                    throw new StockInsufficientException(productVariant.getExternalId().toString());
                }
                existingItem.setQuantity(incomingItemRequest.getQuantity());
                incomingItemsMap.remove(productVariantId);
            } else {
                itemsToRemove.add(existingItem);
            }
        }

        // 2. Eliminar ítems marcados
        for (CartItemEntity item : itemsToRemove) {
            cart.removeCartItem(item);
        }

        // 3. Añadir nuevos ítems (los que quedan en incomingItemsMap)
        for (CartItemRequestDTO newItemRequest : incomingItemsMap.values()) {
            ProductVariantEntity productVariant = productVariantRepository.findByExternalId(newItemRequest.getProductVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("ProductVariant not found with ID:", newItemRequest.getProductVariantId()));

            // Validar si el producto está activo
            if (!productVariant.getActive()) {
                throw new BusinessRuleException("Product variant with ID " + newItemRequest.getProductVariantId() + " is not active and cannot be added to cart.");
            }

            if (productVariant.getStock() < newItemRequest.getQuantity()) {
                throw new StockInsufficientException(productVariant.getExternalId().toString());
            }

            CartItemEntity newCartItem = CartItemEntity.builder()
                    .productVariant(productVariant)
                    .quantity(newItemRequest.getQuantity())
                    .build();
            cart.addCartItem(newCartItem);
        }

        CartEntity savedCart = cartRepository.save(cart);

        return mapCartToResponseDTO(savedCart);
    }

    @Transactional
    public CartResponseDTO updateCartItemQuantity(UUID userExternalId, UUID productVariantId, CartItemQuantityUpdateDTO updateDTO) {
        UserEntity user = getActiveUser(userExternalId);

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:", user.getExternalId()));

        // Buscar el CartItemEntity específico
        CartItemEntity cartItemToUpdate = cart.getCartItems().stream()
                .filter(item -> item.getProductVariant().getExternalId().equals(productVariantId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item for product variant not found with ID: ", productVariantId ));

        Integer newQuantity = updateDTO.getQuantity();

        // Si la nueva cantidad es 0, eliminar el ítem del carrito
        if (newQuantity <= 0) {
            cart.removeCartItem(cartItemToUpdate);
        } else {
            ProductVariantEntity productVariant = cartItemToUpdate.getProductVariant();

            // Validar si el producto está activo (aunque ya esté en el carrito, podría haberse desactivado)
            if (!productVariant.getActive()) {
                throw new BusinessRuleException("Product variant with ID " + productVariantId + " is not active and cannot be updated in cart.");
            }

            // Validar stock para la nueva cantidad
            if (productVariant.getStock() < newQuantity) {
                throw new StockInsufficientException(productVariant.getExternalId().toString());
            }
            cartItemToUpdate.setQuantity(newQuantity);
        }

        CartEntity savedCart = cartRepository.save(cart);
        return mapCartToResponseDTO(savedCart);
    }


    public CartResponseDTO getCartByUserId(UUID userExternalId) {
        UserEntity user = getActiveUser(userExternalId);

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: ", userExternalId));

        return mapCartToResponseDTO(cart);
    }

    @Transactional
    public void clearCart(UUID userExternalId) {
        UserEntity user = getActiveUser(userExternalId);

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: ", userExternalId));

        if (!cart.getCartItems().isEmpty()) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    private CartResponseDTO mapCartToResponseDTO(CartEntity cart) {
        CartResponseDTO responseDTO = modelMapper.map(cart, CartResponseDTO.class);

        List<CartItemResponseDTO> itemDTOs = cart.getCartItems().stream()
                .map(cartItemEntity -> {
                    CartItemResponseDTO itemResponseDTO = modelMapper.map(cartItemEntity, CartItemResponseDTO.class);
                    itemResponseDTO.setProductName(cartItemEntity.getProductVariant().getProduct().getName());
                    itemResponseDTO.setVariantAttribute(cartItemEntity.getProductVariant().getAttribute());

                    ProductVariantEntity productVariant = cartItemEntity.getProductVariant();
                    BigDecimal unitPrice = calculateUnitPrice(
                            productVariant,
                            cartItemEntity.getQuantity());

                    itemResponseDTO.setUnitPrice(unitPrice);

                    return itemResponseDTO;
                })
                .toList();
        responseDTO.setCartItems(itemDTOs);

        BigDecimal totalAmount = itemDTOs.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        responseDTO.setTotalAmount(totalAmount);

        return responseDTO;
    }

    private UserEntity getActiveUser(UUID userId) {
        UserEntity user = userRepository.findByExternalId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: ", userId)
                );

        if (!user.getActive()) {
            throw new BusinessRuleException("User is inactive");
        }

        return user;
    }

    private BigDecimal calculateUnitPrice(ProductVariantEntity variant, Integer quantity) {

        if (variant.getWholesaleMinQty() != null
                && variant.getWholesalePrice() != null
                && quantity >= variant.getWholesaleMinQty()) {
            return variant.getWholesalePrice();
        }

        return variant.getPrice();
    }

    public CartEntity getCartEntityByUserId(UUID userExternalId) {
        UserEntity user = getActiveUser(userExternalId);

        return cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found with ID: ", userExternalId));
    }

    @Transactional
    public OrderResponseDTO checkout(UUID userId, UUID addressId) {
        CartEntity cart = getCartEntityByUserId(userId);

        if(cart.getCartItems().isEmpty()) {
            throw new BusinessRuleException("Cart is empty");
        }

        OrderResponseDTO order = orderService.createFromCart(cart, addressId);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return order;
    }

}