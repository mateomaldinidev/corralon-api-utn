package com.utn.corralon.features.productVariant.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.product.repository.ProductRepository;
import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.mapper.ProductVariantMapper;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductVariantService implements IProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    private final ProductVariantMapper productVariantMapper;

    @Override
    public ProductVariantResponseDTO getById(UUID externalId) {
        ProductVariantEntity variant = productVariantRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found. ID: " + externalId));

        return productVariantMapper.toResponse(variant);
    }

    @Override
    public List<ProductVariantResponseDTO> getAll() {
        return productVariantRepository.findAll()
                .stream()
                .map(productVariantMapper::toResponse)
                .toList();
    }

    @Override
    public ProductVariantResponseDTO create(ProductVariantRequestDTO productVariantRequestDTO) {
        ProductEntity product =
                productRepository.findByExternalId(
                        productVariantRequestDTO.getProductId()
                ).orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found. ID: " + productVariantRequestDTO.getProductId()));

        ProductVariantEntity variant = productVariantMapper.toEntity(
                productVariantRequestDTO,
                product
        );
        productVariantRepository.save(variant);
        return productVariantMapper.toResponse(variant);

    }

    @Override
    public ProductVariantResponseDTO update(UUID externalId, ProductVariantRequestDTO productVariantRequestDTO) {
        ProductVariantEntity variant =
                productVariantRepository.findByExternalId(externalId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Product variant not found. ID: " + externalId));

        ProductEntity product = productRepository
                .findByExternalId(productVariantRequestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found. ID: " + productVariantRequestDTO.getProductId()));

        productVariantMapper.updateEntity(variant, productVariantRequestDTO, product);

        productVariantRepository.save(variant);
        return productVariantMapper.toResponse(variant);
    }

    @Override
    public void delete(UUID externalId) {
        ProductVariantEntity variant = productVariantRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product variant not found. ID: " + externalId));

        productVariantRepository.delete(variant);
    }
}
