package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.categories.dto.CategoriesResponseDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.productVariantEntity;
import jakarta.validation.constraints.NotNull;

public class ProductVariantMapper {
    public static ProductVariantResponseDTO toDTO(@NotNull productVariantEntity productVariant) {
        return new ProductVariantResponseDTO(
                productVariant.getExternalId(),
                productVariant.getAtrivute(),
                productVariant.getPrice(),
                productVariant.getStock(),
                productVariant.getActive(),
                productVariant.getWholesalePrice(),
                productVariant.getWholeMinStock()

        );
    }
}
