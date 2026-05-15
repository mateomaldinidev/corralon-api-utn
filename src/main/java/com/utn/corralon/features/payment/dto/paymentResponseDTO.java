package com.utn.corralon.features.payment.dto;

import com.utn.corralon.features.payment.entity.PaymentMethod;
import com.utn.corralon.features.payment.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record paymentResponseDTO(
        UUID externalId,
        UUID orderExternalId,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt
) {
}
