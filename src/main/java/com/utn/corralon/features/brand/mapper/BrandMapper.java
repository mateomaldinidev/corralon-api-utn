package com.utn.corralon.features.brand.mapper;

import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.brand.dto.BrandResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public ModelMapper modelMapper = new ModelMapper();

    public BrandMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BrandResponseDTO toResponse(BrandEntity brand) {
        return modelMapper.map(brand, BrandResponseDTO.class);
    }

    public BrandEntity toEntity(@NotNull BrandResponseDTO dto) {
        return modelMapper.map(dto, BrandEntity.class);
    }

    public void updateEntity(BrandEntity brand,
                             BrandResponseDTO dto) {
        modelMapper.map(dto, brand);
    }



}
