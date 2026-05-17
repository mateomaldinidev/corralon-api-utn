package com.utn.corralon.features.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDTO {
    @NotBlank
    private String street;

    @NotBlank
    private String streetNumber;


    private String floor;

    private String apartmentNumber;
//nota: le saque el @NotBlank a floor y apartmentNumber porque si yo vivo en una casa (que es lo más comun para comprar materiales de construccion en un corralon), no tengo piso ni número de departamento y si lo dejo en blanco tira error
    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    @NotNull
    private Long userId;
}


