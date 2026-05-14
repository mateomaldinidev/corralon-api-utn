package com.utn.corralon.features.brand.mapper;

import com.utn.corralon.features.brand.brandEntity;
import com.utn.corralon.features.brand.dto.BrandResponseDTO;
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
