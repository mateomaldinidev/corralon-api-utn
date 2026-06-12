package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotNull(message = "Supplier is required")
    private UUID supplierId;

    @NotNull(message = "Category is required")
    private UUID categoryId;

    @NotNull(message = "Brand is required")
    private UUID brandId;

    @NotNull(message = "Active status is required")
    private Boolean active;

}
