package com.utn.corralon.features.user.mapper;

import com.utn.corralon.features.user.dto.UserRequestDTO;
import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO toResponse(UserEntity user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserEntity toEntity(@NotNull UserRequestDTO dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    public void updateEntity(UserEntity user, UserRequestDTO dto) {
        modelMapper.map(dto, user);
    }
}