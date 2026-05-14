package com.utn.corralon.features.role.dto;

import jakarta.validation.constraints.NotNull;
import lombok. *;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class roleRequestDTO {
    @NotNull
    String name;
}
