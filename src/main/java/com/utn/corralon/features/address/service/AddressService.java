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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddressResponseDTO create(AddressRequestDTO dto) {
        AddressEntity entity = addressMapper.toEntity(dto);
        UserEntity user = userRepository.findByExternalId(dto.getUserExternalId()) // Buscar por externalId
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserExternalId(), dto.getUserExternalId())); // Corregido el ID

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
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + externalId, externalId));
        return addressMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public AddressResponseDTO update(UUID externalId, AddressRequestDTO dto){
        AddressEntity entity = addressRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + externalId, externalId));

        addressMapper.updateEntity(entity, dto);

        // Si el usuario asociado a la dirección cambia, buscar y asignar el nuevo usuario
        if (!entity.getUser().getExternalId().equals(dto.getUserExternalId())) {
            UserEntity user = userRepository.findByExternalId(dto.getUserExternalId()) // Buscar por externalId
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserExternalId(), dto.getUserExternalId()));
            entity.setUser(user);
        }

        AddressEntity updatedEntity = addressRepository.save(entity);
        return addressMapper.toResponse(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID externalId) {
        AddressEntity entity = addressRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + externalId, externalId)); // Corregido el ID
        addressRepository.delete(entity);
    }

    @Override
    public List<AddressResponseDTO> getAllByUserExternalId(UUID userExternalId) {
        // Se asume que getActiveUser() no es necesario aquí si solo se listan direcciones
        // Si se necesita validar que el usuario esté activo, se podría llamar a getActiveUser(userExternalId)
        return addressRepository.findAllByUser_ExternalId(userExternalId).stream()
                .map(addressMapper::toResponse)
                .toList();
    }
}