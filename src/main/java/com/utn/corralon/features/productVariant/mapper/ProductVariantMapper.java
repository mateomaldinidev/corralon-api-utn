package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.product.productEntity;
import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.ProductVariantEntity;

public class ProductVariantMapper {

    public ProductVariantResponseDTO toProductVariantResponse(ProductVariantEntity variant) {

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

    public ProductVariantEntity toEntity(
            ProductVariantRequestDTO dto,
            productEntity product
    ) {

        return ProductVariantEntity.builder()
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
            ProductVariantEntity variant,
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
