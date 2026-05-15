package com.utn.corralon.features.payment.dto;

import com.utn.corralon.features.payment.entity.PaymentMethod;
import com.utn.corralon.features.payment.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {
    @NotNull
    private UUID orderId;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PaymentStatus status;

}
