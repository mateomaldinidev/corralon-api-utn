package com.utn.corralon.features.offer.mapper;

import com.utn.corralon.features.offer.dto.offerResponseDTO;
import com.utn.corralon.features.offer.offerEntity;
import com.utn.corralon.features.offer_product.dto.offerProductResponseDTO;
import com.utn.corralon.features.offer_product.mapper.offerProductMapper;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class offerMapper {
    public static offerResponseDTO toDTO(@NotNull offerEntity offer){
        List<offerProductResponseDTO> offerProductsDTO= offer.getOfferProducts().stream()
                .map(offerProductMapper::toDTO)
                .toList();
        return new offerResponseDTO(
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
