package com.utn.corralon.features.user.dto;

import java.util.UUID;

public record userResponseDTO(
        UUID externalId,
        String email,
        String name,
        String lastName
){ }
