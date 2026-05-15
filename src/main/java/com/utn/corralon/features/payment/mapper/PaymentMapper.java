package com.utn.corralon.features.payment.mapper;

import com.utn.corralon.features.payment.dto.paymentResponseDTO;
import com.utn.corralon.features.payment.entity.PaymentEntity;
import jakarta.validation.constraints.NotNull;

public class PaymentMapper {
    public static paymentResponseDTO toDTO(@NotNull PaymentEntity payment){
        return new paymentResponseDTO(
                payment.getExternalId(),
                payment.getOrder().getExternalId(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()

        );
    }
}
