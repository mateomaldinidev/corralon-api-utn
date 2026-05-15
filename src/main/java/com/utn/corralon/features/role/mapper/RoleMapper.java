package com.utn.corralon.features.role.mapper;

import com.utn.corralon.features.role.dto.RoleResponseDTO;
import com.utn.corralon.features.role.entity.RoleEntity;
import jakarta.validation.constraints.NotNull;

public class RoleMapper {
    public static RoleResponseDTO toDTO(@NotNull RoleEntity role){
        return new RoleResponseDTO(
                role.getName()
                );
    }
}
