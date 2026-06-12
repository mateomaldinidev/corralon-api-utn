package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap; // Importar PropertyMap
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private final ModelMapper modelMapper;

    public AddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configuración específica para AddressMapper
        // Usar un PropertyMap para ignorar explícitamente la propiedad 'user'
        // cuando se mapea de AddressRequestDTO a AddressEntity.
        // Esto evita que ModelMapper intente mapear userExternalId (UUID) a user.id (Long)
        modelMapper.addMappings(new PropertyMap<AddressRequestDTO, AddressEntity>() {
            @Override
            protected void configure() {
                skip(destination.getUser()); // Ignorar la propiedad 'user' en la entidad de destino
            }
        });
    }

    public AddressResponseDTO toResponse(AddressEntity address) {
        AddressResponseDTO dto =
                modelMapper.map(address, AddressResponseDTO.class);

        // Asegurarse de que el userExternalId se mapee correctamente desde el UserEntity
        if (address.getUser() != null) {
            dto.setUserExternalId(address.getUser().getExternalId());
        }
        return dto;

    }

    public AddressEntity toEntity(AddressRequestDTO dto) {
        // ModelMapper ahora ignorará la propiedad 'user' gracias a la configuración del PropertyMap
        AddressEntity entity = modelMapper.map(dto, AddressEntity.class);
        return entity;
    }

    public void updateEntity(AddressEntity entity, AddressRequestDTO dto) {
        // ModelMapper ahora ignorará la propiedad 'user' gracias a la configuración del PropertyMap
        modelMapper.map(dto, entity);
    }

}