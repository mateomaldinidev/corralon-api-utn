package com.utn.corralon.features.user.dto;

import com.utn.corralon.features.auth.Roles;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private UUID externalId;
    private String email;
    private String name;
    private String lastName;
    private Roles role;
    private Boolean active;
}
