package com.utn.corralon.features.brand.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class brandResponseDTO {
    @NotNull
    private UUID externalId;

    @NotBlank
    private String name;

    private boolean active;
}
