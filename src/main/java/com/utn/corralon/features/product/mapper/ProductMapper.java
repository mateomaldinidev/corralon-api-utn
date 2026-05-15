package com.utn.corralon.features.product.mapper;

import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.category.entity.CategoryEntity;
import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.supplier.entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductResponseDTO toResponse(ProductEntity product) {
        ProductResponseDTO response =
                modelMapper.map(product, ProductResponseDTO.class);

        response.setSupplierId(product.getSupplier().getExternalId());
        response.setSupplierName(product.getSupplier().getName());

        response.setCategoryId(product.getCategory().getExternalId());
        response.setCategoryName(product.getCategory().getName());

        response.setBrandId(product.getBrand().getExternalId());
        response.setBrandName(product.getBrand().getName());

        return response;
    }

    public ProductEntity toEntity(ProductRequestDTO dto,
                                  SupplierEntity supplier,
                                  CategoryEntity category,
                                  BrandEntity brand) {
        ProductEntity product = modelMapper.map(dto, ProductEntity.class);

        product.setSupplier(supplier);
        product.setCategory(category);
        product.setBrand(brand);

        return product;
    }

    public void updateEntity(ProductEntity product,
                             ProductRequestDTO dto,
                             SupplierEntity supplier,
                             CategoryEntity category,
                             BrandEntity brand) {
        modelMapper.map(dto, product);

        product.setSupplier(supplier);
        product.setCategory(category);
        product.setBrand(brand);
    }


}