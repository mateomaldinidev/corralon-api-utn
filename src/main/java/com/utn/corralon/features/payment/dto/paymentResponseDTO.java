package com.utn.corralon.features.payment.dto;

import com.utn.corralon.features.payment.paymentMethod;
import com.utn.corralon.features.payment.paymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record paymentResponseDTO(
        UUID externalId,
        UUID orderExternalId,
        paymentMethod paymentMethod,
        BigDecimal amount,
        paymentStatus status,
        LocalDateTime createdAt
) {
}
