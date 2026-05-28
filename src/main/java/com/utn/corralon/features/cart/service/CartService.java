package com.utn.corralon.features.cart.service;

import com.utn.corralon.features.cart.dto.CartRequestDTO;
import com.utn.corralon.features.cart.dto.CartResponseDTO;
import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart.repository.CartRepository;
import com.utn.corralon.features.cart_item.dto.CartItemRequestDTO;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import com.utn.corralon.features.cart_item.entity.CartItemEntity;
import com.utn.corralon.features.cart_item.repository.CartItemRepository;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository; // Para buscar usuarios
    private final ProductVariantRepository productVariantRepository; // Para buscar variantes de producto
    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       ProductVariantRepository productVariantRepository,
                       ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CartResponseDTO createOrUpdateCart(CartRequestDTO cartRequest) {
        //Buscar o crear el usuario
        UserEntity user = userRepository.findByExternalId(cartRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + cartRequest.getUserId()));

        //Buscar el carrito existente del usuario o crear uno nuevo
        CartEntity cart = cartRepository.findByUser(user)
                .orElseGet(() -> CartEntity.builder()
                        .user(user)
                        .cartItems(new ArrayList<>()) // Inicializar la lista de items para evitar nullpointer mas adelante
                        .build());

        // Limpiar items existentes si es una actualizacion completa (o ajustar logica si es solo añadir/modificar)
        // Para este ejemplo, asumimos que el request representa el estado deseado del carrito
        // Si el carrito ya tiene ítems, los eliminamos para reemplazarlos con los del request
        if (cart.getId() != null && !cart.getCartItems().isEmpty()) {
            cart.getCartItems().clear(); // Esto activa orphanRemoval si está configurado
        }

        //Procesar los items del request
        for (CartItemRequestDTO itemRequest : cartRequest.getItems()) {
            ProductVariantEntity productVariant = productVariantRepository.findByExternalId(itemRequest.getProductVariantId())
                    .orElseThrow(() -> new RuntimeException("Product variant not found with ID: " + itemRequest.getProductVariantId()));

            //verificar stock
            if (productVariant.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for product variant: " + productVariant.getExternalId());
            }

            CartItemEntity cartItem = CartItemEntity.builder()
                    .productVariant(productVariant)
                    .quantity(itemRequest.getQuantity())
                    .build();

            // Usamos el metodo de conveniencia de la entidad para sincronizar la relacion
            cart.addCartItem(cartItem);
        }

        // Guardar el carrito (esto acutaliza los items debido a CascadeType.ALL)
        CartEntity savedCart = cartRepository.save(cart);

        //mapear a DTO de respuesta y calcular el total
        return mapCartToResponseDTO(savedCart);
    }

    public CartResponseDTO getCartByUserId(UUID userId) {
        UserEntity user = userRepository.findByExternalId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));

        return mapCartToResponseDTO(cart);
    }
    @Transactional
    public void clearCart(UUID userId) {
        UserEntity user = userRepository.findByExternalId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));

        if (!cart.getCartItems().isEmpty()) {
            cart.getCartItems().clear(); // Esto activa orphanRemoval para eliminar los ítems de la DB
            cartRepository.save(cart); // Guarda el carrito para que los cambios se persistan y @PreUpdate actualice lastUpdated
        }
    }

    // Metodo auxiliar para mapear CartEntity a CartResponseDTO y calcular el total
    private CartResponseDTO mapCartToResponseDTO(CartEntity cart) {
        CartResponseDTO responseDTO = modelMapper.map(cart, CartResponseDTO.class);

        // Mapear los ítems del carrito
        List<CartItemResponseDTO> itemDTOs = cart.getCartItems().stream()
                .map(cartItemEntity -> {
                    CartItemResponseDTO itemResponseDTO = modelMapper.map(cartItemEntity, CartItemResponseDTO.class);
                    // Rellenar detalles del producto/variante que no están directamente en CartItemEntity
                    itemResponseDTO.setProductName(cartItemEntity.getProductVariant().getProduct().getName()); // Asume que ProductVariant tiene un Product
                    itemResponseDTO.setVariantAttribute(cartItemEntity.getProductVariant().getAttribute());
                    itemResponseDTO.setUnitPrice(cartItemEntity.getProductVariant().getPrice());
                    return itemResponseDTO;
                })
                .toList();
        responseDTO.setCartItems(itemDTOs);

        // Calcular el total del carrito
        BigDecimal totalAmount = itemDTOs.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))//el valueOf convierte el int en bigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);//acumulador en 0 y el :: add va sumando los resultados en ese acumulador
        responseDTO.setTotalAmount(totalAmount);

        return responseDTO;
    }

}
