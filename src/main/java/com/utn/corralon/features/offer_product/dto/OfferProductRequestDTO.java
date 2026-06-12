package com.utn.corralon.features.offer_product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OfferProductRequestDTO {
    @NotNull(message = "Offer ID is required")
    private UUID offerId;

    @NotNull(message = "Product variant ID is required")
    private UUID productVariantId;

    @NotNull(message = "Discounted price is required")
    @Positive(message = "Discounted price must be greater than 0")
    private BigDecimal discountedPrice;
}
