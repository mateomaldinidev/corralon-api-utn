package com.utn.corralon.features.cart_item.service;

import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart.repository.CartRepository;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import com.utn.corralon.features.cart_item.entity.CartItemEntity;
import com.utn.corralon.features.cart_item.repository.CartItemRepository;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository; // Necesario para actualizar el carrito padre
    private final ProductVariantRepository productVariantRepository; // Necesario para validaciones de stock
    private final ModelMapper modelMapper;

    public CartItemService(CartItemRepository cartItemRepository,
                           CartRepository cartRepository,
                           ProductVariantRepository productVariantRepository,
                           ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productVariantRepository = productVariantRepository;
        this.modelMapper = modelMapper;
    }

    public CartItemResponseDTO getCartItemByExternalId(UUID externalId) {
        CartItemEntity cartItem = cartItemRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + externalId));
        return mapCartItemToResponseDTO(cartItem);
    }

    @Transactional
    public CartItemResponseDTO updateCartItemQuantity(UUID externalId, Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero.");
        }

        CartItemEntity cartItem = cartItemRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + externalId));

        ProductVariantEntity productVariant = cartItem.getProductVariant();
        if (productVariant.getStock() < newQuantity) {
            throw new RuntimeException("Not enough stock for product variant: " + productVariant.getExternalId());
        }

        cartItem.setQuantity(newQuantity);
        CartItemEntity updatedCartItem = cartItemRepository.save(cartItem);

        // Actualizar el carrito padre (lastUpdated)
        CartEntity parentCart = updatedCartItem.getCart();
        cartRepository.save(parentCart); // Guardar para que se actualice la fecha

        return mapCartItemToResponseDTO(updatedCartItem);
    }

    @Transactional
    public void deleteCartItem(UUID externalId) {
        CartItemEntity cartItem = cartItemRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + externalId));

        // Eliminar el item del carrito padre para que orphanRemoval funcione
        CartEntity parentCart = cartItem.getCart();
        parentCart.removeCartItem(cartItem); // Usa el metodo de conveniencia de CartEntity

        cartRepository.save(parentCart); // Guarda el carrito padre para que se propague la eliminación
    }

    // Metodo auxiliar para mapear CartItemEntity a CartItemResponseDTO
    private CartItemResponseDTO mapCartItemToResponseDTO(CartItemEntity cartItemEntity) {
        CartItemResponseDTO itemResponseDTO = modelMapper.map(cartItemEntity, CartItemResponseDTO.class);
        // Rellenar detalles del producto/variante
        itemResponseDTO.setProductName(cartItemEntity.getProductVariant().getProduct().getName());
        itemResponseDTO.setVariantAttribute(cartItemEntity.getProductVariant().getAttribute());
        itemResponseDTO.setUnitPrice(cartItemEntity.getProductVariant().getPrice());
        return itemResponseDTO;
    }
}