package com.utn.corralon.features.brands.mapper;

import com.utn.corralon.features.brands.brandEntity;
import com.utn.corralon.features.brands.dto.BrandResponseDTO;
import jakarta.validation.constraints.NotNull;

public class BrandMapper {

    public static BrandResponseDTO toDTO(@NotNull brandEntity brand) {
        return new BrandResponseDTO(
                brand.getExternalId(),
                brand.getName(),
                brand.getActive()
        );
    }
}
