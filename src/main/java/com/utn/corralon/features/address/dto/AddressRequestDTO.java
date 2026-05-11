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

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    @NotNull
    private Long userId;
}


