package com.utn.corralon.features.product.dto;

import java.util.UUID;

public record productResponseDTO(
        UUID externalId,
        String description,
        boolean active
) { }
