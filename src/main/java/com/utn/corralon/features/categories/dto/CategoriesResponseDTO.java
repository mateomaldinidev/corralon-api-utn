package com.utn.corralon.features.categories.dto;

import java.util.UUID;

public record CategoriesResponseDTO(
        UUID externalId,
        String name,
        boolean active
) { }
