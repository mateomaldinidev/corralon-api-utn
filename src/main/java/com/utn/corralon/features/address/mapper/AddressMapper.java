package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.addressEntity;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AddressMapper {

    @Contract("_ -> new")
    public static @NotNull AddressResponseDTO toDTO(@NotNull addressEntity address) {
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