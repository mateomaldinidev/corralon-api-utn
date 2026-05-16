package com.utn.corralon.features.address.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.mapper.AddressMapper;
import com.utn.corralon.features.address.repository.AddressRepository;
import com.utn.corralon.features.user.entity.UserEntity;
import com.utn.corralon.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    public AddressResponseDTO create(AddressRequestDTO dto) {
        AddressEntity entity = addressMapper.toEntity(dto);
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        entity.setUser(user);
        AddressEntity savedEntity = addressRepository.save(entity);
        return addressMapper.toResponse(savedEntity);
    }

    @Override
    public List<AddressResponseDTO> getAll() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    public AddressResponseDTO getByExternalId(UUID externalId) {
        AddressEntity entity = addressRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        return addressMapper.toResponse(entity);
    }

    @Override
    public AddressResponseDTO update(UUID externalId, AddressRequestDTO dto){
        AddressEntity entity = addressRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressMapper.updateEntity(entity, dto);
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        entity.setUser(user);
        AddressEntity updatedEntity = addressRepository.save(entity);
        return addressMapper.toResponse(updatedEntity);
    }

    @Override
    public void delete(UUID externalId) {
        AddressEntity entity = addressRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressRepository.delete(entity);
    }

}
