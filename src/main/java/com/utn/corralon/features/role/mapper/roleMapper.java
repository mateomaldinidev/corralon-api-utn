package com.utn.corralon.features.role.mapper;

import com.utn.corralon.features.role.dto.roleResponseDTO;
import com.utn.corralon.features.role.roleEntity;
import jakarta.validation.constraints.NotNull;

public class roleMapper {
    public static roleResponseDTO toDTO(@NotNull roleEntity role){
        return new roleResponseDTO(
                role.getName()
                );
    }
}
