package com.utn.corralon.features.role.mapper;

import com.utn.corralon.features.role.dto.roleResponseDTO;
import com.utn.corralon.features.role.entity.RoleEntity;
import jakarta.validation.constraints.NotNull;

public class RoleMapper {
    public static roleResponseDTO toDTO(@NotNull RoleEntity role){
        return new roleResponseDTO(
                role.getName()
                );
    }
}
