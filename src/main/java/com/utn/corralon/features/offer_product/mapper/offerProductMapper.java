package com.utn.corralon.features.offer_product.mapper;

import com.utn.corralon.features.offer_product.dto.offerProductResponseDTO;
import com.utn.corralon.features.offer_product.entity.OfferProductEntity;
import jakarta.validation.constraints.NotNull;

public class offerProductMapper {
    public static offerProductResponseDTO toDTO(@NotNull OfferProductEntity offerProduct){
        return new offerProductResponseDTO(
                offerProduct.getOffer().getExternalId(),
                offerProduct.getProductVariant().getExternalId(),
                offerProduct.getDiscountedPrice()
        );
    }
}
