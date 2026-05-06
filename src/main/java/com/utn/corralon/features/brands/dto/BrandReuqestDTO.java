package com.utn.corralon.features.brands.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BrandReuqestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private Boolean active;

}
