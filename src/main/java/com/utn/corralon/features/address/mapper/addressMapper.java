package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.addressEntity;
import com.utn.corralon.features.address.dto.addressRequestDTO;
import com.utn.corralon.features.address.dto.addressResponseDTO;
import com.utn.corralon.features.user.userEntity;

public class addressMapper {

    public static addressResponseDTO toAddressResponse(addressEntity address) {
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

    public static addressEntity toAddressEntity(addressRequestDTO addressRequestDTO){
        return addressEntity.builder()
                .street(addressRequestDTO.getStreet())
                .streetNumber(addressRequestDTO.getStreetNumber())
                .floor(addressRequestDTO.getFloor())
                .apartmentNumber(addressRequestDTO.getApartmentNumber())
                .city(addressRequestDTO.getCity())
                .zipCode(addressRequestDTO.getZipCode())
                .user(userEntity.builder().build()) //revisar este punto
                .build();
    }
}