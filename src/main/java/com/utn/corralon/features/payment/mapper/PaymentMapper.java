package com.utn.corralon.features.payment.mapper;

import com.utn.corralon.features.payment.dto.PaymentResponseDTO;
import com.utn.corralon.features.payment.entity.PaymentEntity;
import jakarta.validation.constraints.NotNull;

public class PaymentMapper {
    public static PaymentResponseDTO toDTO(@NotNull PaymentEntity payment){
        return new PaymentResponseDTO(
                payment.getExternalId(),
                payment.getOrder().getExternalId(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()

        );
    }
}
