package com.utn.corralon.features.payment.mapper;

import com.utn.corralon.features.payment.dto.paymentResponseDTO;
import com.utn.corralon.features.payment.paymentEntity;
import jakarta.validation.constraints.NotNull;

public class paymentMapper {
    public static paymentResponseDTO toDTO(@NotNull paymentEntity payment){
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
