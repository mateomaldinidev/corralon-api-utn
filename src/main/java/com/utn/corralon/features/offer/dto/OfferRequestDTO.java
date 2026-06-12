package com.utn.corralon.features.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferRequestDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Discount percentage is required")
    @Positive(message = "Discount percentage must be greater than 0")
    private BigDecimal discountPercentage;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
}
