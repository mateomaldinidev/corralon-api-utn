package com.utn.corralon.features.cart_item.mapper;

import com.utn.corralon.features.cart_item.cartItemEntity;
import com.utn.corralon.features.cart_item.dto.cartItemResponseDTO;
import jakarta.validation.constraints.NotNull;

public class cartItemMapper {
    public static cartItemResponseDTO toDTO(@NotNull cartItemEntity cartItem){
        return new cartItemResponseDTO(
                cartItem.getExternalId(),
                cartItem.getCart().getExternalId(),
                cartItem.getProductVariant().getExternalId(),
                cartItem.getQuantity(),
                cartItem.getCreatedAt()


        );
    }
}
