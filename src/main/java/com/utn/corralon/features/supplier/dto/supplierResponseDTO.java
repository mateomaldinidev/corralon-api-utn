package com.utn.corralon.features.supplier.dto;

import java.util.UUID;

public record supplierResponseDTO(
    UUID externalId,
    String name,
    String contact

    ) { }
