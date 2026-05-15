package com.utn.corralon.features.addresses.mapper;

import com.utn.corralon.features.addresses.addressEntity;
import com.utn.corralon.features.addresses.dto.AddressRequestDTO;
import com.utn.corralon.features.addresses.dto.AddressResponseDTO;
import com.utn.corralon.features.users.userEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public addressEntity toEntity(
            AddressRequestDTO request,
            userEntity user
    ) {

        return addressEntity.builder()
                .street(request.getStreet())
                .streetNumber(request.getStreetNumber())
                .floor(request.getFloor())
                .apartmentNumber(request.getApartmentNumber())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .user(user)
                .build();
    }

    public AddressResponseDTO toResponse(addressEntity address) {

        return AddressResponseDTO.builder()
                .externalId(address.getExternalId())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .floor(address.getFloor())
                .apartmentNumber(address.getApartmentNumber())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .userExternalId(address.getUser().getExternalId())
                .build();
    }
}