package com.utn.corralon.features.user.mapper;

import com.utn.corralon.features.user.dto.userResponseDTO;
import com.utn.corralon.features.user.userEntity;

public class userMapper {
    public static userResponseDTO toDTO(userEntity user){
        return new userResponseDTO(
                user.getExternalId(),
                user.getEmail(),
                user.getName(),
                user.getLastName()
        );
    }
}
