package com.utn.corralon.features.supplier.mapper;

import com.utn.corralon.features.supplier.dto.supplierResponseDTO;
import com.utn.corralon.features.supplier.supplierEntity;
import jakarta.validation.constraints.NotNull;

public class supplierMapper {
    public static supplierResponseDTO toDTO(@NotNull supplierEntity supplier){
        return new supplierResponseDTO(
                supplier.getExternalId(),
                supplier.getName(),
                supplier.getContact()
        );
    }
}
