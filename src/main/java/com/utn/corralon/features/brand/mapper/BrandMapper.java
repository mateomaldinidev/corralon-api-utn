package com.utn.corralon.features.brand.mapper;

import com.utn.corralon.features.brand.BrandEntity;
import com.utn.corralon.features.brand.dto.brandResponseDTO;
import jakarta.validation.constraints.NotNull;

public class BrandMapper {

    public static brandResponseDTO toDTO(@NotNull BrandEntity brand) {
        return new brandResponseDTO(
                brand.getExternalId(),
                brand.getName(),
                brand.getActive()
        );
    }
}
