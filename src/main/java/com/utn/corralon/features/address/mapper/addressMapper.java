package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.addressEntity;
import com.utn.corralon.features.address.dto.addressResponseDTO;
import jakarta.validation.constraints.NotNull;


public class addressMapper {

    public static @NotNull addressResponseDTO toDTO(@NotNull addressEntity address) {
        return new addressResponseDTO(
                address.getExternalId(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getFloor(),
                address.getApartmentNumber(),
                address.getCity(),
                address.getZipCode(),
                address.getUser().getId()
        );
    }
}