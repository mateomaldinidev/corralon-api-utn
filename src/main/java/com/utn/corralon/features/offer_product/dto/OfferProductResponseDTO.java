package com.utn.corralon.features.offer_product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OfferProductResponseDTO(
        UUID offerExternalId,
        UUID productVariantExternalId,
        BigDecimal discountedPrice


) { }
