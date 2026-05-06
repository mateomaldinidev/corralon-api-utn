package com.utn.corralon.features.categories.mapper;

import com.utn.corralon.features.categories.categorieEntity;
import com.utn.corralon.features.categories.dto.CategoriesResponseDTO;
import jakarta.validation.constraints.NotNull;

public class CategoriesMapper {

    public static CategoriesResponseDTO toDTO(@NotNull categorieEntity categorie){
        return new CategoriesResponseDTO(
                categorie.getExternalId(),
                categorie.getName(),
                categorie.getActive()
        );
    }
}
