package com.utn.corralon.features.brands.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BrandResponseDTO{
    @NotNull
    private UUID externalId;

    @NotBlank
    private String name;

    private boolean active;
}
