package com.utn.corralon.features.product.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;



public record productResponseDTO(
     UUID externalId,

     String description,
     boolean active,

     UUID supplierExternalId,
     String supplierName,

     UUID categorieExternalId,
     String categorieName,

     UUID brandExternalId,
     String brandName
){ }