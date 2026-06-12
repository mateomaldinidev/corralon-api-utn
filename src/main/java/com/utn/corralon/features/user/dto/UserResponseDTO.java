package com.utn.corralon.features.user.dto;

import com.utn.corralon.features.user.enums.RoleEnum;

import java.util.UUID;

public record UserResponseDTO( // record en vez de class para response
        UUID externalId,
        String email,
        String name,
        String lastName,
        RoleEnum role,
        Boolean active
) { }
