package com.utn.corralon.features.role.mapper;

import com.utn.corralon.features.role.dto.RoleRequestDTO;
import com.utn.corralon.features.role.dto.RoleResponseDTO;
import com.utn.corralon.features.role.roleEntity;
import jakarta.validation.constraints.NotNull;

public class RoleMapper {
    public static RoleResponseDTO toDTO(@NotNull roleEntity role){
        return new RoleResponseDTO(
                role.getName()
                );
    }
}
