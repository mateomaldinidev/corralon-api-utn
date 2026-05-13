package com.utn.corralon.features.categories.mapper;

import com.utn.corralon.features.categories.categorieEntity;
import com.utn.corralon.features.categories.dto.CategoriesRequestDTO;
import com.utn.corralon.features.categories.dto.CategoriesResponseDTO;

public class CategoriesMapper {

    public static CategoriesResponseDTO toResponseCategorie(categorieEntity categorie){
        return  CategoriesResponseDTO.builder()
                .externalId(categorie.getExternalId())
                .name(categorie.getName())
                .active(categorie.getActive())
                .build();
    }

    public static categorieEntity toCategorieEntity(CategoriesRequestDTO categoriesRequestDTO){
        return categorieEntity.builder()
                .name(categoriesRequestDTO.getName())
                .active(categoriesRequestDTO.getActive())
                .build();
    }

}
