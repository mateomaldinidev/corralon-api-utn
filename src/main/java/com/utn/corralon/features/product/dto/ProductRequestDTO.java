package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {
    @NotBlank
    private String description;

    @NotBlank
    private boolean active;

    @NotBlank
    private UUID supplierId;

    @NotBlank
    private UUID categoryId;

    @NotBlank
    private UUID brandId;

    public boolean getActive() {
        return true;    }//VER ES PARA SACAR EL ERROR

}
