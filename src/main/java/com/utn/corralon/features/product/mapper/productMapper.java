package com.utn.corralon.features.product.mapper;

import com.utn.corralon.features.product.dto.productResponseDTO;
import com.utn.corralon.features.product.productEntity;
import jakarta.validation.constraints.NotNull;

public class productMapper {
    public static productResponseDTO toDTO(@NotNull productEntity product){
        return new productResponseDTO(
                product.getExternalId(),
                product.getDescription(),
                product.getActive()
        );
    }
}