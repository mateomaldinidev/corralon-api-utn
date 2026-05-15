package com.utn.corralon.features.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.* ;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    @NotBlank
    private Boolean active;
}
