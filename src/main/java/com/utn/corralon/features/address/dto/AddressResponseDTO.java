package com.utn.corralon.features.address.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddressResponseDTO{

    private UUID externalId;

    private String street;

    private String streetNumber;

    private String floor;

    private String apartmentNumber;

    private String city;

    private String zipCode;

    private UUID userExternalId;

}

