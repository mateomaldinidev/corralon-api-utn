package com.utn.corralon.features.supplier.controller;

import com.utn.corralon.features.supplier.dto.SupplierRequestDTO;
import com.utn.corralon.features.supplier.dto.SupplierResponseDTO;
import com.utn.corralon.features.supplier.service.ISupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;

    // Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> create(@RequestBody @Valid SupplierRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.create(dto));
    }

    // Listar todos los proveedores activos
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    // Buscar un proveedor por su ID
    @GetMapping("/{externalId}")
    public ResponseEntity<SupplierResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(supplierService.getByExternalId(externalId));
    }

    // Actualizar un proveedor existente
    @PutMapping("/{externalId}")
    public ResponseEntity<SupplierResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid SupplierRequestDTO dto) {
        return ResponseEntity.ok(supplierService.update(externalId, dto));
    }

    // Eliminar un proveedor (baja lógica)
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        supplierService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    // Activar un proveedor inactivo
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        supplierService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    // Listar todos los proveedores inactivos
    @GetMapping("/inactive")
    public ResponseEntity<List<SupplierResponseDTO>> getInactive() {
        return ResponseEntity.ok(supplierService.getInactive());
    }
}
