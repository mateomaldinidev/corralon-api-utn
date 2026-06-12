package com.utn.corralon.features.supplier.controller;

import com.utn.corralon.features.supplier.dto.SupplierRequestDTO;
import com.utn.corralon.features.supplier.dto.SupplierResponseDTO;
import com.utn.corralon.features.supplier.service.ISupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLIER_CREATE')")
    public ResponseEntity<SupplierResponseDTO> create(@RequestBody @Valid SupplierRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPPLIER_LIST')")
    public ResponseEntity<List<SupplierResponseDTO>> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/{externalId}")
    @PreAuthorize("hasAuthority('SUPPLIER_READ')")
    public ResponseEntity<SupplierResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(supplierService.getByExternalId(externalId));
    }

    @PutMapping("/{externalId}")
    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    public ResponseEntity<SupplierResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid SupplierRequestDTO dto) {
        return ResponseEntity.ok(supplierService.update(externalId, dto));
    }

    @DeleteMapping("/{externalId}")
    @PreAuthorize("hasAuthority('SUPPLIER_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        supplierService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{externalId}/activate")
    @PreAuthorize("hasAuthority('SUPPLIER_ACTIVATE')")
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        supplierService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    // Listar todos los proveedores inactivos
    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('SUPPLIER_LIST_INACTIVE')")
    public ResponseEntity<List<SupplierResponseDTO>> getInactive() {
        return ResponseEntity.ok(supplierService.getInactive());
    }
}
