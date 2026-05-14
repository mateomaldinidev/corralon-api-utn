package com.utn.corralon.features.cart.mapper;

import com.utn.corralon.features.cart.cartEntity;
import com.utn.corralon.features.cart_item.dto.cartItemResponseDTO;
import com.utn.corralon.features.cart.dto.cartResponseDTO;
import com.utn.corralon.features.cart_item.mapper.cartItemMapper;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class cartMapper {

    public static cartResponseDTO toDTO(@NotNull cartEntity cart){
        List<cartItemResponseDTO> itemsDTO= cart.getCartItems().stream()
                .map(cartItemMapper::toDTO)
                .toList();
        return new cartResponseDTO(
                cart.getExternalId(),
                cart.getUser().getExternalId(),
                cart.getLastUpdated(),
                itemsDTO
        );
    }
}
