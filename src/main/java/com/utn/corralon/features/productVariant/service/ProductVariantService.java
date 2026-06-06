package com.utn.corralon.features.productVariant.service;

import com.utn.corralon.exception.BusinessRuleException;
import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.product.repository.ProductRepository;
import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.productVariant.mapper.ProductVariantMapper;
import com.utn.corralon.features.productVariant.repository.ProductVariantRepository;
import com.utn.corralon.features.productVariant.specification.ProductVariantSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                .findByExternalIdAndActiveTrue(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found. ID: " + externalId, userId));

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
                        "Product not found. ID: " + productVariantRequestDTO.getProductId(), userId));
        if (!product.isActive())
        {
            throw new BusinessRuleException("Cannot create variant for inactive product");
        }
        if(productVariantRepository.existsByProductAndAttribute(
                product,
                productVariantRequestDTO.getAttribute()))
        {
            throw new BusinessRuleException("Product variant already exists");
        }

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
                                "Product variant not found. ID: " + externalId, userId));

        ProductEntity product = productRepository
                .findByExternalId(productVariantRequestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found. ID: " + productVariantRequestDTO.getProductId(), userId));
        if (!product.isActive()) {
            throw new BusinessRuleException("Cannot assign inactive product");
        }

        productVariantMapper.updateEntity(variant, productVariantRequestDTO, product);

        productVariantRepository.save(variant);
        return productVariantMapper.toResponse(variant);
    }

    @Override
    public void delete(UUID externalId) {
        ProductVariantEntity variant = productVariantRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product variant not found. ID: " + externalId, userId));

        if (!variant.getActive()) {
            throw new BusinessRuleException("Product variant alreadt inactive.");
        }
        variant.setActive(false);
        productVariantRepository.save(variant);
    }

    @Override
    public List<ProductVariantResponseDTO> search(String attribute,
                                                  Boolean active,
                                                  BigDecimal minPrice,
                                                  BigDecimal maxPrice,
                                                  Integer minStock,
                                                  UUID productId,
                                                  UUID categoryId,
                                                  UUID brandId,
                                                  String productName) {
        Specification<ProductVariantEntity> specification =
                Specification.where(
                                ProductVariantSpecification.hasAttribute(attribute)
                        .and(
                                ProductVariantSpecification.isActive(active)
                        )
                        .and(
                                ProductVariantSpecification.hasMinPrice(minPrice)
                        )
                        .and(
                                ProductVariantSpecification.hasMaxPrice(maxPrice)
                        )
                        .and(
                                ProductVariantSpecification.hasMinStock(minStock)
                        )
                        .and(
                                ProductVariantSpecification.hasProduct(productId)
                        )
                        .and(
                                ProductVariantSpecification.hasCategory(categoryId)
                        )
                        .and(
                                ProductVariantSpecification.hasBrand(brandId)
                        )
                        .and(
                                ProductVariantSpecification.hasProductName(productName  )
                        ));

        return productVariantRepository.findAll(specification)
                .stream()
                .map(productVariantMapper::toResponse)
                .toList();

    }
}
