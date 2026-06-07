package com.utn.corralon.features.brand.controller;

import com.utn.corralon.features.brand.dto.BrandRequestDTO;
import com.utn.corralon.features.brand.dto.BrandResponseDTO;
import com.utn.corralon.features.brand.service.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor

public class BrandController {
    private final IBrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDTO> create(@RequestBody @Valid BrandRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAll() {
        return ResponseEntity.ok(brandService.getAll());
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<BrandResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(brandService.getByExternalId(externalId));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BrandResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid BrandRequestDTO dto) {
        return ResponseEntity.ok(brandService.update(externalId, dto));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        brandService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    // Nuevo endpoint para activar una marca
    @PatchMapping("/{externalId}/activate") // PATCH es más semantico para activar/desactivar
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        brandService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    // Nuevo endpoint para obtener marcas inactivas
    @GetMapping("/inactive")
    public ResponseEntity<List<BrandResponseDTO>> getInactive() {
        return ResponseEntity.ok(brandService.getInactive());
    }
}