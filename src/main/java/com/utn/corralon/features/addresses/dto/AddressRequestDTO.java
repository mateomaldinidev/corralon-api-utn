package com.utn.corralon.features.addresses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String streetNumber;

    @NotBlank
    private String floor;

    @NotBlank
    private String apartmentNumber;

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    @NotNull
    private UUID userExternalId;
}