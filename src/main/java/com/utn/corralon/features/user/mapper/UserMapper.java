package com.utn.corralon.features.user.mapper;

import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.entity.UserEntity;

public class UserMapper {
    public static UserResponseDTO toDTO(UserEntity user){
        return new UserResponseDTO(
                user.getExternalId(),
                user.getEmail(),
                user.getName(),
                user.getLastName()
        );
    }
}
