package com.utn.corralon.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class productRequestDTO {
    @NotBlank
    private String description;

    @NotBlank
    private boolean active;

    public boolean getActive() {
        return true;    }//VER ES PARA SACAR EL ERROR

}
