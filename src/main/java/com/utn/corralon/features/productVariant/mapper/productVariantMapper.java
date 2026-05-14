package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.product.productEntity;
import com.utn.corralon.features.productVariant.dto.productVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.productVariantResponseDTO;
import com.utn.corralon.features.productVariant.productVariantEntity;

public class productVariantMapper {

    public productVariantResponseDTO toProductVariantResponse(productVariantEntity variant) {
        return new productVariantResponseDTO(
                variant.getExternalId(),
                variant.getAttribute(),
                variant.getPrice(),
                variant.getStock(),
                variant.getActive(),
                variant.getWholesalePrice(),
                variant.getWholeMinStock(),
                variant.getProduct().getExternalId(),
                variant.getProduct().getDescription()
        );
    }

    public productVariantEntity toEntity(
            productVariantRequestDTO dto,
            productEntity product
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
            productVariantRequestDTO dto,
            productEntity product
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