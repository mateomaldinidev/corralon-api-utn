package com.utn.corralon.features.category.mapper;

import com.utn.corralon.features.category.dto.CategoryResponseDTO;
import com.utn.corralon.features.category.entity.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public ModelMapper modelMapper = new ModelMapper();

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryResponseDTO toResponse(CategoryEntity category) {
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public CategoryEntity toEntity(CategoryResponseDTO dto) {
        return modelMapper.map(dto, CategoryEntity.class);
    }

    public void updateEntity(CategoryEntity category,
                             CategoryResponseDTO dto) {
        modelMapper.map(dto, category);
    }
}
