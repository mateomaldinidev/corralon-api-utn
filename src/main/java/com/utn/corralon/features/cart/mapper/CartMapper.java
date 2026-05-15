package com.utn.corralon.features.cart.mapper;

import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import com.utn.corralon.features.cart.dto.CartResponseDTO;
import com.utn.corralon.features.cart_item.mapper.CartItemMapper;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CartMapper {

    public static CartResponseDTO toDTO(@NotNull CartEntity cart){
        List<CartItemResponseDTO> itemsDTO= cart.getCartItems().stream()
                .map(CartItemMapper::toDTO)
                .toList();
        return new CartResponseDTO(
                cart.getExternalId(),
                cart.getUser().getExternalId(),
                cart.getLastUpdated(),
                itemsDTO
        );
    }
}
