package com.utn.corralon.features.product.mapper;

import com.utn.corralon.features.brands.brandEntity;
import com.utn.corralon.features.categories.categorieEntity;
import com.utn.corralon.features.product.ProductEntity;
import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.suppliers.supplierEntity;

public class ProductMapper {
    public ProductResponseDTO toResponse(ProductEntity product) {

        return ProductResponseDTO.builder()
                .externalId(product.getExternalId())
                .description(product.getDescription())
                .active(product.isActive())

                .supplierExternalId(product.getSupplier().getExternalId())
                .supplierName(product.getSupplier().getName())

                .categorieExternalId(product.getCategorie().getExternalId())
                .categorieName(product.getCategorie().getName())

                .brandExternalId(product.getBrand().getExternalId())
                .brandName(product.getBrand().getName())

                .build();
    }

    public ProductEntity toEntity(
            ProductRequestDTO dto,
            supplierEntity supplier,
            categorieEntity categorie,
            brandEntity brand
    ) {

        return ProductEntity.builder()
                .description(dto.getDescription())
                .active(dto.getActive())
                .supplier(supplier)
                .categorie(categorie)
                .brand(brand)
                .build();
    }

    public void updateEntity(
            ProductEntity product,
            ProductRequestDTO dto,
            supplierEntity supplier,
            categorieEntity categorie,
            brandEntity brand
    ) {

        product.setDescription(dto.getDescription());
        product.setActive(dto.getActive());
        product.setSupplier(supplier);
        product.setCategorie(categorie);
        product.setBrand(brand);
    }
}

