package com.utn.corralon.features.addresses;

import com.utn.corralon.features.addresses.dto.AddressRequestDTO;
import com.utn.corralon.features.addresses.dto.AddressResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    AddressResponseDTO create(AddressRequestDTO request);

    List<AddressResponseDTO> findAll();

    AddressResponseDTO findByExternalId(UUID externalId);

    AddressResponseDTO update(
            UUID externalId,
            AddressRequestDTO request
    );

    void delete(UUID externalId);
}

