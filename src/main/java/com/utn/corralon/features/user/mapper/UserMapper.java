package com.utn.corralon.features.user.mapper;

import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.userEntity;

public class UserMapper {
    public static UserResponseDTO toDTO(userEntity user){
        return new UserResponseDTO(
                user.getExternalId(),
                user.getEmail(),
                user.getName(),
                user.getLastName()
        );
    }
}
