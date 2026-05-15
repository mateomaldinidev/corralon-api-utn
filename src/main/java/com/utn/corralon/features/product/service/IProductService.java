package com.utn.corralon.features.product.service;

import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;


import java.util.List;
import java.util.UUID;

public interface IProductService {
    ProductResponseDTO getById(UUID externalId);
    List<ProductResponseDTO> getAll();
    ProductResponseDTO create(ProductRequestDTO productRequestDTO);
    ProductResponseDTO update(UUID externalId,
                              ProductRequestDTO productRequestDTO);
    void delete(UUID externalId);

}
