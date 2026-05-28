package com.utn.corralon.features.productVariant.service;

import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IProductVariantService {

    ProductVariantResponseDTO getById(UUID externalId);

    List<ProductVariantResponseDTO> getAll();
    List<ProductVariantResponseDTO> search(String attribute,
                                           Boolean active,
                                           BigDecimal minPrice,
                                           BigDecimal maxPrice,
                                           Integer minStock,
                                           UUID productId,
                                           UUID categoryId,
                                           UUID brandId,
                                           String productName);

    ProductVariantResponseDTO create(ProductVariantRequestDTO productVariantRequestDTO);

    ProductVariantResponseDTO update(UUID externalId,
                                     ProductVariantRequestDTO productVariantRequestDTO);

    void delete(UUID externalId);
}
