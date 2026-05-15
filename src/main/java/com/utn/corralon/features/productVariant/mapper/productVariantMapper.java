package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class productVariantMapper {
    private final ModelMapper modelMapper;

    public productVariantMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductVariantResponseDTO toResponse(ProductVariantEntity variant) {
        ProductVariantResponseDTO dto =
                modelMapper.map(
                        variant,
                        ProductVariantResponseDTO.class);
        dto.setProductId(variant.getProduct().getExternalId());
        dto.setProductDescription(variant.getProduct().getDescription());
        return dto;
    }

    public ProductVariantEntity toEntity(ProductVariantResponseDTO dto,
                                         ProductEntity product) {
        ProductVariantEntity variant =
                modelMapper.map(
                        dto,
                        ProductVariantEntity.class);

        variant.setProduct(product);
        return variant;
    }

    public void updateEntity(ProductVariantEntity variant,
                        ProductVariantResponseDTO dto,
                        ProductEntity product) {
        modelMapper.map(dto, variant);
        variant.setProduct(product);

    }
}
