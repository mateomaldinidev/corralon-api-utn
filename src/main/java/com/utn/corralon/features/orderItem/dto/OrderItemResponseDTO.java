package com.utn.corralon.features.orderItem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private UUID externalId;

    private UUID productVariantExternalId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;

}
