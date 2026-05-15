package com.utn.corralon.features.brand.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponseDTO {

    @NotBlank
    private UUID externalId;

    @NotBlank
    private String name;

    @NotBlank
    private Boolean active;
}
