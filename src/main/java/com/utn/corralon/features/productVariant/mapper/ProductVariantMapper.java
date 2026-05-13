package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.product.ProductEntity;
import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.productVariantEntity;
import jakarta.validation.constraints.NotNull;

public class ProductVariantMapper {

    public ProductVariantResponseDTO toProductVariantResponse(productVariantEntity variant) {

        return ProductVariantResponseDTO.builder()
                .externalId(variant.getExternalId())
                .attribute(variant.getAttribute())
                .price(variant.getPrice())
                .stock(variant.getStock())
                .active(variant.getActive())
                .wholesalePrice(variant.getWholesalePrice())
                .wholeMinStock(variant.getWholeMinStock())

                .productExternalId(variant.getProduct().getExternalId())
                .productDescription(variant.getProduct().getDescription())

                .build();
    }

    public productVariantEntity toEntity(
            ProductVariantRequestDTO dto,
            ProductEntity product
    ) {

        return productVariantEntity.builder()
                .attribute(dto.getAttribute())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .active(dto.getActive())
                .wholesalePrice(dto.getWholesalePrice())
                .wholeMinStock(dto.getWholeMinStock())
                .product(product)
                .build();
    }

    public void updateEntity(
            productVariantEntity variant,
            ProductVariantRequestDTO dto,
            ProductEntity product
    ) {

        variant.setAttribute(dto.getAttribute());
        variant.setPrice(dto.getPrice());
        variant.setStock(dto.getStock());
        variant.setActive(dto.getActive());
        variant.setWholesalePrice(dto.getWholesalePrice());
        variant.setWholeMinStock(dto.getWholeMinStock());
        variant.setProduct(product);
    }
}
