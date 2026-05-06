package com.utn.corralon.features.product.dto;

import java.util.UUID;

public record ProductResponseDTO (
        UUID externalId,
        String description,
        boolean active
) { }
