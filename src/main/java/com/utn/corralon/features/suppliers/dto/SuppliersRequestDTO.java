package com.utn.corralon.features.suppliers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.* ;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SuppliersRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    @NotBlank
    private Boolean active;
}
