package com.utn.corralon.features.offer_product.mapper;

import com.utn.corralon.features.offer_product.dto.offerProductResponseDTO;
import com.utn.corralon.features.offer_product.offerProductEntity;
import jakarta.validation.constraints.NotNull;

public class offerProductMapper {
    public static offerProductResponseDTO toDTO(@NotNull offerProductEntity offerProduct){
        return new offerProductResponseDTO(
                offerProduct.getOffer().getExternalId(),
                offerProduct.getProductVariant().getExternalId(),
                offerProduct.getDiscountedPrice()
        );
    }
}
