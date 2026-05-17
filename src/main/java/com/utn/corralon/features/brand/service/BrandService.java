package com.utn.corralon.features.brand.service;

import com.utn.corralon.exception.ResourceNotFoundException;
import com.utn.corralon.features.brand.dto.BrandRequestDTO;
import com.utn.corralon.features.brand.dto.BrandResponseDTO;
import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.brand.mapper.BrandMapper;
import com.utn.corralon.features.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponseDTO create(BrandRequestDTO dto) {
        // Validamos si ya existe (como hablamos antes)
        if (brandRepository.existsByName(dto.getName())) {
            throw new RuntimeException("La marca ya existe");
        }
        BrandEntity entity = brandMapper.toEntity(dto);
        entity.setActive(true);
        BrandEntity saved = brandRepository.save(entity);
        return brandMapper.toResponse(saved);
    }

    @Override
    public List<BrandResponseDTO> getAll() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toResponse)
                .toList();
    }

    @Override

    public BrandResponseDTO getByExternalId(UUID externalId) {
        return brandRepository.findByExternalId(externalId)
                .map(brandMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found ID: " + externalId));
    }
    @Override
    public BrandResponseDTO update(UUID externalId, BrandRequestDTO dto) {
        BrandEntity entity = brandRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be updated. Brand not found."));

        brandMapper.updateEntity(entity, dto);
        BrandEntity updated = brandRepository.save(entity);
        return brandMapper.toResponse(updated);
    }

    @Override
    public void delete(UUID externalId) {
        BrandEntity entity = brandRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot be updated. Brand not found."));
        entity.setActive(false);
        brandRepository.save(entity);
    }
}

