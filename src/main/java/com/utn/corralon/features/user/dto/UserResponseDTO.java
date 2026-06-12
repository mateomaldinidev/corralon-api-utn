package com.utn.corralon.features.user.dto;

import com.utn.corralon.features.user.enums.RoleEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID externalId;
    private String email;
    private String name;
    private String lastName;
    private RoleEnum role;
    private Boolean active;

}
