package com.utn.corralon.features.brand.dto;

import java.util.UUID;

public  record brandResponseDTO(
        UUID externalId,
        String name,
        boolean active

) {}
