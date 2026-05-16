package com.utn.corralon.features.address.service;

import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.dto.AddressResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IAddressService {

    AddressResponseDTO create(AddressRequestDTO address);

    List<AddressResponseDTO> getAll();

    AddressResponseDTO getByExternalId(UUID externalId);

    AddressResponseDTO update(UUID externalId, AddressRequestDTO dto);

    void delete(UUID externalId);
}
