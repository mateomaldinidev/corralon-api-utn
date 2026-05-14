package com.utn.corralon.features.offer_product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class offerProductRequestDTO {
    @NotNull
    private UUID offerId;

    @NotNull
    private UUID productVariantId;

    @NotNull
    private BigDecimal discountedPrice;
}
