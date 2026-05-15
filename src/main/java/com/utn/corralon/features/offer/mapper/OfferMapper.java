package com.utn.corralon.features.offer.mapper;

import com.utn.corralon.features.offer.dto.OfferResponseDTO;
import com.utn.corralon.features.offer.entity.OfferEntity;
import com.utn.corralon.features.offer_product.dto.OfferProductResponseDTO;
import com.utn.corralon.features.offer_product.mapper.OfferProductMapper;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OfferMapper {
    public static OfferResponseDTO toDTO(@NotNull OfferEntity offer){
        List<OfferProductResponseDTO> offerProductsDTO= offer.getOfferProducts().stream()
                .map(OfferProductMapper::toDTO)
                .toList();
        return new OfferResponseDTO(
                offer.getExternalId(),
                offer.getName(),
                offer.getDiscountPercentage(),
                offer.getStartDate(),
                offer.getEndDate(),
                offer.getActive(),
                offerProductsDTO
        );
    }
}
