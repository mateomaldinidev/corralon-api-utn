package com.utn.corralon.features.addresses;

import com.utn.corralon.features.addresses.dto.AddressRequestDTO;
import com.utn.corralon.features.addresses.dto.AddressResponseDTO;
import com.utn.corralon.features.addresses.mapper.AddressMapper;
import com.utn.corralon.features.users.UserRepository;
import com.utn.corralon.features.users.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    public AddressResponseDTO create(AddressRequestDTO request) {

        userEntity user = userRepository
                .findByExternalId(request.getUserExternalId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        addressEntity address = addressMapper.toEntity(
                request,
                user
        );

        return addressMapper.toResponse(
                addressRepository.save(address)
        );
    }

    @Override
    public List<AddressResponseDTO> findAll() {

        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    public AddressResponseDTO findByExternalId(UUID externalId) {

        addressEntity address = addressRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        return addressMapper.toResponse(address);
    }

    @Override
    public AddressResponseDTO update(
            UUID externalId,
            AddressRequestDTO request
    ) {

        addressEntity address = addressRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        userEntity user = userRepository
                .findByExternalId(request.getUserExternalId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        address.setStreet(request.getStreet());
        address.setStreetNumber(request.getStreetNumber());
        address.setFloor(request.getFloor());
        address.setApartmentNumber(request.getApartmentNumber());
        address.setCity(request.getCity());
        address.setZipCode(request.getZipCode());
        address.setUser(user);

        return addressMapper.toResponse(
                addressRepository.save(address)
        );
    }

    @Override
    public void delete(UUID externalId) {

        addressEntity address = addressRepository
                .findByExternalId(externalId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }
}