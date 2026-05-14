package com.utn.corralon.features.brand.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class brandRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private Boolean active;

}
