package com.utn.corralon.features.orders.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private UUID externalId;

    private UUID userExternalId;

    private UUID addressExternalId;

    private BigDecimal total;

    private LocalDateTime created_at;

    private Boolean active;
}