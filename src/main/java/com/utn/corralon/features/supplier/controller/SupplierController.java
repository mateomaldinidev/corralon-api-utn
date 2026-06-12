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

    @PreAuthorize("hasAuthority('SUPPLIER_CREATE')")
    @PostMapping
    public ResponseEntity<SupplierResponseDTO> create(@RequestBody @Valid SupplierRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.create(dto));
    }

    @PreAuthorize("hasAuthority('SUPPLIER_LIST')")
    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @PreAuthorize("hasAuthority('SUPPLIER_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<SupplierResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(supplierService.getByExternalId(externalId));
    }

    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    @PutMapping("/{externalId}")
    public ResponseEntity<SupplierResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid SupplierRequestDTO dto) {
        return ResponseEntity.ok(supplierService.update(externalId, dto));
    }

    @PreAuthorize("hasAuthority('SUPPLIER_DELETE')")
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        supplierService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SUPPLIER_ACTIVATE')")
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        supplierService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SUPPLIER_LIST_INACTIVE')")
    @GetMapping("/inactive")
    public ResponseEntity<List<SupplierResponseDTO>> getInactive() {
        return ResponseEntity.ok(supplierService.getInactive());
    }
}
