package com.utn.corralon.features.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO{
    @NotBlank
    private UUID externalId;
    @NotBlank
    private String name;
    @NotBlank
    private boolean active;
}
