package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import jakarta.validation.constraints.NotNull;


public class AddressMapper {

    public static @NotNull AddressResponseDTO toDTO(@NotNull AddressEntity address) {
        return new AddressResponseDTO(
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