package com.utn.corralon.features.offer_product.mapper;

import com.utn.corralon.features.offer_product.dto.OfferProductResponseDTO;
import com.utn.corralon.features.offer_product.entity.OfferProductEntity;
import jakarta.validation.constraints.NotNull;

public class OfferProductMapper {
    public static OfferProductResponseDTO toDTO(@NotNull OfferProductEntity offerProduct){
        return new OfferProductResponseDTO(
                offerProduct.getOffer().getExternalId(),
                offerProduct.getProductVariant().getExternalId(),
                offerProduct.getDiscountedPrice()
        );
    }
}
