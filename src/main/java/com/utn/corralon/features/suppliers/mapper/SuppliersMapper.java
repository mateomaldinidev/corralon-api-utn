package com.utn.corralon.features.suppliers.mapper;

import com.utn.corralon.features.suppliers.dto.SuppliersResponseDTO;
import com.utn.corralon.features.suppliers.supplierEntity;
import jakarta.validation.constraints.NotNull;

public class SuppliersMapper {
    public static SuppliersResponseDTO toDTO(@NotNull supplierEntity supplier){
        return new SuppliersResponseDTO(
                supplier.getExternalId(),
                supplier.getName(),
                supplier.getContact()
        );
    }
}
