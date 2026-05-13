package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductRequestDTO {

    @NotBlank
    private String description;

    @NotNull
    private Boolean active;

    @NotNull
    private UUID supplierExternalId;

    @NotNull
    private UUID categorieExternalId;

    @NotNull
    private UUID brandExternalId;
}
