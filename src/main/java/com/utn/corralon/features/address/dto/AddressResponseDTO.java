package com.utn.corralon.features.address.dto;

import lombok. *;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddressResponseDTO {
    @NotNull
    UUID externalId;

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


