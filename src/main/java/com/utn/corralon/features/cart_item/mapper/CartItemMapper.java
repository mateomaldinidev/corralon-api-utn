package com.utn.corralon.features.cart_item.mapper;

import com.utn.corralon.features.brand.dto.BrandRequestDTO;
import com.utn.corralon.features.brand.dto.BrandResponseDTO;
import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.cart_item.dto.CartItemResponseDTO;
import com.utn.corralon.features.cart_item.entity.CartItemEntity;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;

public class CartItemMapper {
    public ModelMapper modelMapper = new ModelMapper();

    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartItemResponseDTO toResponse(CartItemEntity cart) {
        return modelMapper.map(cart, CartItemResponseDTO.class);
    }

    public CartItemEntity toEntity(@NotNull CartItemResponseDTO dto) {
        return modelMapper.map(dto, CartItemEntity.class);
    }

    public void updateEntity(CartItemEntity cart,
                             CartItemResponseDTO dto) {
        modelMapper.map(dto, cart);
    }
}
