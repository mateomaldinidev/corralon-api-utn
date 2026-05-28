package com.utn.corralon.features.cart.mapper;

import com.utn.corralon.features.brand.dto.BrandRequestDTO;
import com.utn.corralon.features.brand.dto.BrandResponseDTO;
import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.cart.dto.CartRequestDTO;
import com.utn.corralon.features.cart.dto.CartResponseDTO;
import com.utn.corralon.features.cart.entity.CartEntity;
import com.utn.corralon.features.cart_item.mapper.CartItemMapper;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CartMapper {

    public ModelMapper modelMapper = new ModelMapper();

    public CartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartResponseDTO toResponse(CartEntity cart) {
        return modelMapper.map(cart, CartResponseDTO.class);
    }

    public CartEntity toEntity(@NotNull CartRequestDTO dto) {
        return modelMapper.map(dto, CartEntity.class);
    }

    public void updateEntity(CartEntity cart,
                             CartRequestDTO dto) {
        modelMapper.map(dto, cart);
    }
}
