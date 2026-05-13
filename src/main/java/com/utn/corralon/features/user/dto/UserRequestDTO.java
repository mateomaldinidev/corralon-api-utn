package com.utn.corralon.features.user.dto;

import jakarta.validation.constraints.NotBlank;
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


}
