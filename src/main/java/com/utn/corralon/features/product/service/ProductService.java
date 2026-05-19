package com.utn.corralon.features.product.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.brand.repository.BrandRepository;
import com.utn.corralon.features.category.entity.CategoryEntity;
import com.utn.corralon.features.category.repository.CategoryRepository;
import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.product.mapper.ProductMapper;
import com.utn.corralon.features.product.repository.ProductRepository;
import com.utn.corralon.features.supplier.entity.SupplierEntity;
import com.utn.corralon.features.supplier.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    private final ProductMapper productMapper;


    @Override
    public ProductResponseDTO getById(UUID externalId) {
        ProductEntity product = productRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. ID: " + externalId));
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        SupplierEntity supplier =
                supplierRepository.findByExternalId(
                        productRequestDTO.getSupplierId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Supplier not found. ID: " + productRequestDTO.getSupplierId()));

        CategoryEntity category =
                categoryRepository.findByExternalId(
                        productRequestDTO.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Category not found. ID: " + productRequestDTO.getCategoryId()));

        BrandEntity brand =
                brandRepository.findByExternalId(
                        productRequestDTO.getBrandId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Brand not found. ID: " + productRequestDTO.getBrandId()));

        ProductEntity product = productMapper.toEntity(
                productRequestDTO,
                supplier,
                category,
                brand);

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDTO update(
            UUID externalId,
            ProductRequestDTO productRequestDTO) {
        ProductEntity product = productRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. ID: " + externalId));

        SupplierEntity supplier =
                supplierRepository.findByExternalId(
                        productRequestDTO.getSupplierId())
                        .orElseThrow(() -> new ResourceNotFoundException("Supplier not found. ID: " + productRequestDTO.getSupplierId()));

        CategoryEntity category =
                categoryRepository.findByExternalId(
                        productRequestDTO.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found. ID: " + productRequestDTO.getCategoryId()));

        BrandEntity brand =
                brandRepository.findByExternalId(
                        productRequestDTO.getBrandId())
                        .orElseThrow(() -> new ResourceNotFoundException("Brand not found. ID: " + productRequestDTO.getBrandId()));

        productMapper.updateEntity(
                product,
                productRequestDTO,
                supplier,
                category,
                brand
        );

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    public void delete(UUID externalId) {
        ProductEntity product = productRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found. ID: " + externalId));
        productRepository.delete(product);
    }
}
