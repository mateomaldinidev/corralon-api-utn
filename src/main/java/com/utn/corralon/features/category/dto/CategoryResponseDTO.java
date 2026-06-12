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

    private UUID externalId;

    private String name;

    private boolean active;
}
