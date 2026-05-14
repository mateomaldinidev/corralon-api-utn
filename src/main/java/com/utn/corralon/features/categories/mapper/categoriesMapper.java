package com.utn.corralon.features.categories.mapper;

import com.utn.corralon.features.categories.categoriesEntity;
import com.utn.corralon.features.categories.dto.categoriesResponseDTO;
import jakarta.validation.constraints.NotNull;

public class categoriesMapper {

    public static categoriesResponseDTO toDTO(@NotNull categoriesEntity categorie){
        return new categoriesResponseDTO(
                categorie.getExternalId(),
                categorie.getName(),
                categorie.getActive()
        );
    }
}
