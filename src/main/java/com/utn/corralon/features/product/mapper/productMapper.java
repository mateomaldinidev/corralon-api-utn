package com.utn.corralon.features.product.mapper;

import com.utn.corralon.features.brand.brandEntity;
import com.utn.corralon.features.categories.categoriesEntity;
import com.utn.corralon.features.product.productEntity;
import com.utn.corralon.features.product.dto.productRequestDTO;
import com.utn.corralon.features.product.dto.productResponseDTO;
import com.utn.corralon.features.supplier.supplierEntity;

public class productMapper {
    public productResponseDTO toResponse(productEntity product) {
        return new productResponseDTO(
                product.getExternalId(),
                product.getDescription(),
                product.getActive(),
                product.getSupplier().getExternalId(),
                product.getSupplier().getName(),
                product.getCategorie().getExternalId(),
                product.getCategorie().getName(),
                product.getBrand().getExternalId(),
                product.getBrand().getName()
        );
    }

    public productEntity toEntity(
            productRequestDTO dto,
            supplierEntity supplier,
            categoriesEntity categorie,
            brandEntity brand
    ) {
        return productEntity.builder()
                .description(dto.getDescription())
                .active(dto.getActive())
                .supplier(supplier)
                .categorie(categorie)
                .brand(brand)
                .build();
    }

    public void updateEntity(
            productEntity product,
            productRequestDTO dto,
            supplierEntity supplier,
            categoriesEntity categorie,
            brandEntity brand
    ) {
        product.setDescription(dto.getDescription());
        product.setActive(dto.getActive());
        product.setSupplier(supplier);
        product.setCategorie(categorie);
        product.setBrand(brand);
    }
}