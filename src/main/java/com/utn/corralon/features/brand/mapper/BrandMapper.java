package com.utn.corralon.features.brand.mapper;

import com.utn.corralon.features.brand.brandEntity;
import com.utn.corralon.features.brand.dto.brandRequestDTO;
import com.utn.corralon.features.brand.dto.brandResponseDTO;

public class brandMapper {

    public static brandResponseDTO toBrandResponse(brandEntity brand) {
        return brandResponseDTO.builder()
                .externalId(brand.getExternalId())
                .name(brand.getName())
                .active(brand.isActive())
                .build();

    }

    public static brandEntity toEntity(brandRequestDTO brandRequest){
        return  brandEntity.builder()
                .name(brandRequest.getName())
                .active(brandRequest.getActive())
                .build();
    }
}
