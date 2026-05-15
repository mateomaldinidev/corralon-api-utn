package com.utn.corralon.features.supplier.mapper;


import com.utn.corralon.features.supplier.dto.SupplierResponseDTO;
import com.utn.corralon.features.supplier.entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public ModelMapper modelMapper = new ModelMapper();

    public SupplierMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SupplierResponseDTO toResponse(SupplierEntity supplier) {
        return modelMapper.map(supplier, SupplierResponseDTO.class);
    }

    public SupplierEntity toEntity(SupplierResponseDTO dto) {
        return modelMapper.map(dto, SupplierEntity.class);
    }

    public void updateEntity(SupplierEntity supplier, SupplierResponseDTO dto) {
        modelMapper.map(dto, supplier);
    }

}
