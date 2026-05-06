package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductRequestDTO {
    @NotBlank
    private String description;

    @NotBlank
    private boolean active;
}
