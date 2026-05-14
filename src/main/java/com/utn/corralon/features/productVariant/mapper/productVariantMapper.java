package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.productVariant.dto.productVariantResponseDTO;
import com.utn.corralon.features.productVariant.productVariantEntity;
import jakarta.validation.constraints.NotNull;

public class productVariantMapper {
    public static productVariantResponseDTO toDTO(@NotNull productVariantEntity productVariant){
        return new productVariantResponseDTO(
                productVariant.getExternalId(),
                productVariant.getAttribute(),
                productVariant.getPrice(),
                productVariant.getStock(),
                productVariant.getActive(),
                productVariant.getWholesalePrice(),
                productVariant.getWholeMinStock()
        );
    }
}
