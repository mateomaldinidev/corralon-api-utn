package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SecondaryRow;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO{
    @NotBlank
    private UUID externalId;
    @NotBlank
    private String description;
    @NotBlank
    private Boolean active;

    private UUID supplierId;
    private String supplierName;

    private UUID categoryId;
    private String categoryName;

    private UUID brandId;
    private String brandName;

}


