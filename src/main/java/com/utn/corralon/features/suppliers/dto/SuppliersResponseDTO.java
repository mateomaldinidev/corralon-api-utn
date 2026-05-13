package com.utn.corralon.features.suppliers.dto;

import java.util.UUID;

public record SuppliersResponseDTO(
    UUID externalId,
    String name,
    String contact

    ) { }
