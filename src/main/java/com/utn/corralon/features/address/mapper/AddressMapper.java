package com.utn.corralon.features.address.mapper;

import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private final ModelMapper modelMapper;

    public AddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Definir un custom Converter para AddressRequestDTO a AddressEntity
        Converter<AddressRequestDTO, AddressEntity> requestDtoToEntityConverter = new Converter<AddressRequestDTO, AddressEntity>() {
            @Override
            public AddressEntity convert(MappingContext<AddressRequestDTO, AddressEntity> context) {
                AddressRequestDTO source = context.getSource();
                // Si destination es null, crear una nueva instancia; de lo contrario, actualizar la existente
                AddressEntity destination = context.getDestination() != null ? context.getDestination() : new AddressEntity();

                // Mapear manualmente las propiedades, excluyendo explícitamente 'id' y 'user'
                // 'id' es autogenerado y 'user' se asigna manualmente en el servicio
                destination.setStreet(source.getStreet());
                destination.setStreetNumber(source.getStreetNumber());
                destination.setFloor(source.getFloor());
                destination.setApartmentNumber(source.getApartmentNumber());
                destination.setCity(source.getCity());
                destination.setZipCode(source.getZipCode());
                // Se elimina la línea que causaba el error, ya que AddressRequestDTO no tiene 'active'
                // destination.setActive(source.getActive()); // Esta línea causaba el error

                // No mapeamos la propiedad 'user' aquí. El servicio se encargará de asignarla.

                return destination;
            }
        };

        // Registrar el custom Converter para el par de tipos específico
        modelMapper.addConverter(requestDtoToEntityConverter);

        // Para AddressEntity a AddressResponseDTO, podemos seguir confiando en el mapeo implícito
        // o añadir configuraciones específicas si es necesario.
        // El error actual es en la dirección DTO -> Entity.
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
        // Esto ahora usará el custom Converter registrado, que mapea las propiedades
        // y evita tocar 'id' y 'user'.
        AddressEntity entity = modelMapper.map(dto, AddressEntity.class);
        return entity;
    }

    public void updateEntity(AddressEntity entity, AddressRequestDTO dto) {
        // Esto también usará el custom Converter, mapeando el dto sobre la entidad existente.
        modelMapper.map(dto, entity);
    }

}