package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.addressEntity;
import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import com.utn.corralon.features.user.userEntity;

public class AddressMapper {


    public static  AddressResponseDTO toAddressResponse(addressEntity address) {
        return AddressResponseDTO.builder()
                .externalId(address.getExternalId())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .floor(address.getFloor())
                .apartmentNumber((address.getApartmentNumber()))
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .userId(address.getUser().getId())
                .build();
    }

    public static addressEntity toAddressEntity(AddressRequestDTO addressRequestDTO){
        return addressEntity.builder()
                .street(addressRequestDTO.getStreet())
                .streetNumber(addressRequestDTO.getStreetNumber())
                .floor(addressRequestDTO.getFloor())
                .apartmentNumber(addressRequestDTO.getApartmentNumber())
                .city(addressRequestDTO.getCity())
                .zipCode(addressRequestDTO.getZipCode())
                .user(userEntity.builder().build()) ///revisar este punto
                .build();
    }
}