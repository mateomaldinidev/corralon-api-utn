package com.utn.corralon.features.address.dto;


import java.util.UUID;

public record AddressResponseDTO(
        UUID externalId,
        String street,
        String streetNumber,
        String floor,
        String apartmentNumber,
        String city,
        String zipCode,
        Long userId
) {}

