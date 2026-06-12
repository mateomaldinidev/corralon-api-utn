package com.utn.corralon.features.productVariant.mapper;

import com.utn.corralon.features.product.entity.ProductEntity;
import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantMapper {
    private final ModelMapper modelMapper;

    public ProductVariantMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configurar ModelMapper para usar estrategia de coincidencia estricta globalmente
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Definir un PropertyMap para ProductVariantRequestDTO a ProductVariantEntity
        modelMapper.addMappings(new PropertyMap<ProductVariantRequestDTO, ProductVariantEntity>() {
            @Override
            protected void configure() {
                // Ignorar el mapeo del ID de la entidad, ya que es autogenerado (Long)
                // Esto previene el error de conversión de UUID a Long
                skip(destination.getId());

                // Ignorar el mapeo de la entidad Product, ya que se asigna manualmente en el servicio
                skip(destination.getProduct());
            }
        });
    }

    public ProductVariantResponseDTO toResponse(ProductVariantEntity variant) {
        ProductVariantResponseDTO dto =
                modelMapper.map(
                        variant,
                        ProductVariantResponseDTO.class);
        dto.setProductId(variant.getProduct().getExternalId());
        dto.setProductName(variant.getProduct().getName());
        return dto;
    }

    public ProductVariantEntity toEntity(ProductVariantRequestDTO dto,
                                         ProductEntity product) {
        // ModelMapper ahora usará el PropertyMap configurado
        ProductVariantEntity variant =
                modelMapper.map(
                        dto,
                        ProductVariantEntity.class);

        // Asignar manualmente la entidad Product después del mapeo del DTO
        variant.setProduct(product);
        return variant;
    }

    public void updateEntity(ProductVariantEntity variant,
                        ProductVariantRequestDTO dto,
                        ProductEntity product) {
        // ModelMapper ahora usará el PropertyMap configurado
        modelMapper.map(dto, variant);
        variant.setProduct(product);
    }
}