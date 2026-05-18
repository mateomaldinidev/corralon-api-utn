package com.utn.corralon.features.user.dto;

import com.utn.corralon.features.user.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;

    @NotNull
    private RoleEnum role;
}
