package com.utn.corralon.features.brands.dto;

import java.util.UUID;

public  record BrandResponseDTO(
        UUID externalId,
        String name,
        boolean active

) {}
