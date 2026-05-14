package com.utn.corralon.features.categoriy.dto;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID externalId,
        String name,
        boolean active
) { }
