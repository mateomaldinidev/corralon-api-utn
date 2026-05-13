package com.utn.corralon.features.categories.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoriesResponseDTO {
    @NotNull
    private UUID externalId;

    @NotNull
    private String name;

    @NotNull
    private boolean active;

}
