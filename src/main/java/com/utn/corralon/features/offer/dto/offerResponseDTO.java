package com.utn.corralon.features.offer.dto;

import com.utn.corralon.features.offer_product.dto.offerProductResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record offerResponseDTO(
    UUID externalId,
    String name,
    BigDecimal discountPercentage,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean active,
    List<offerProductResponseDTO> offerProducts
){}
