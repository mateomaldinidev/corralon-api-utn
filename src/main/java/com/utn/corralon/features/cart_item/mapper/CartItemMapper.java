package com.utn.corralon.features.cart_item.mapper;

import com.utn.corralon.features.cart_item.entity.CartItemEntity;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import jakarta.validation.constraints.NotNull;

public class CartItemMapper {
    public static CartItemResponseDTO toDTO(@NotNull CartItemEntity cartItem){
        return new CartItemResponseDTO(
                cartItem.getExternalId(),
                cartItem.getCart().getExternalId(),
                cartItem.getProductVariant().getExternalId(),
                cartItem.getQuantity(),
                cartItem.getCreatedAt()


        );
    }
}
