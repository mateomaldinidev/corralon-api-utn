package com.utn.corralon.features.payment.dto;

import com.utn.corralon.features.payment.paymentMethod;
import com.utn.corralon.features.payment.paymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class paymentRequestDTO {
    @NotNull
    private UUID orderId;
    @NotNull
    private paymentMethod paymentMethod;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private paymentStatus status;

}
