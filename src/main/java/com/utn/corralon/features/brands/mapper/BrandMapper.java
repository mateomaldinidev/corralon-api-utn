package com.utn.corralon.features.brands.mapper;

import com.utn.corralon.features.brands.brandEntity;
import com.utn.corralon.features.brands.dto.BrandResponseDTO;
import com.utn.corralon.features.brands.dto.BrandReuqestDTO;


public class BrandMapper {

    public static BrandResponseDTO toBrandResponse(brandEntity brand) {
        return BrandResponseDTO.builder()
                .externalId(brand.getExternalId())
                .name(brand.getName())
                .active(brand.isActive())
                .build();

    }

    public static brandEntity toEntity(BrandReuqestDTO brandRequest){
        return  brandEntity.builder()
                .name(brandRequest.getName())
                .active(brandRequest.getActive())
                .build();
    }
}
