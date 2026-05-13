package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductResponseDTO{
    private UUID externalId;

    private String description;

    private boolean active;

    private UUID supplierExternalId;
    private String supplierName;

    private UUID categorieExternalId;
    private String categorieName;

    private UUID brandExternalId;
    private String brandName;



}
