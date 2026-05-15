package com.utn.corralon.features.user.mapper;

import com.utn.corralon.features.user.dto.userResponseDTO;
import com.utn.corralon.features.user.entity.UserEntity;

public class UserMapper {
    public static userResponseDTO toDTO(UserEntity user){
        return new userResponseDTO(
                user.getExternalId(),
                user.getEmail(),
                user.getName(),
                user.getLastName()
        );
    }
}
