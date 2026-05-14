package com.utn.corralon.features.offer.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class offerRequestDTO {
    @NotNull
    private UUID externalId;
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal discount;
    @NotNull
    private boolean active;



}
