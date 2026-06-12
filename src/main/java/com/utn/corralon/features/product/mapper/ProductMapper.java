package com.utn.corralon.features.product.mapper;

import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.category.entity.CategoryEntity;
import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.supplier.entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap; // Importar TypeMap
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configuración específica para ProductMapper
        TypeMap<ProductRequestDTO, ProductEntity> typeMap = modelMapper.createTypeMap(ProductRequestDTO.class, ProductEntity.class);

        // Ignorar el mapeo de las propiedades de las entidades relacionadas y el ID interno
        // El servicio se encargará de buscar y asignar las entidades completas
        typeMap.addMappings(mapper -> {
            mapper.skip(ProductEntity::setId); // Ignorar el ID interno de ProductEntity
            mapper.skip(ProductEntity::setSupplier); // Ignorar la entidad Supplier
            mapper.skip(ProductEntity::setCategory); // Ignorar la entidad Category
            mapper.skip(ProductEntity::setBrand);    // Ignorar la entidad Brand
        });

        // Después de añadir las reglas explícitas, permitir los mapeos implícitos para el resto
        typeMap.implicitMappings();
    }

    public ProductResponseDTO toResponse(ProductEntity product) {
        ProductResponseDTO response = modelMapper.map(product, ProductResponseDTO.class);

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
        // ModelMapper ahora ignorará las propiedades configuradas gracias a la configuración de TypeMap
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
        // ModelMapper ahora ignorará las propiedades configuradas gracias a la configuración de TypeMap
        modelMapper.map(dto, product);

        product.setSupplier(supplier);
        product.setCategory(category);
        product.setBrand(brand);
    }
}