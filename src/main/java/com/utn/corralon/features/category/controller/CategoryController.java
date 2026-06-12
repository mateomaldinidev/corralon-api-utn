package com.utn.corralon.features.category.controller;

import com.utn.corralon.features.category.dto.CategoryRequestDTO;
import com.utn.corralon.features.category.dto.CategoryResponseDTO;
import com.utn.corralon.features.category.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CATEGORY_LIST')")
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{externalId}")
    @PreAuthorize("hasAuthority('CATEGORY_READ')")
    public ResponseEntity<CategoryResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(categoryService.getByExternalId(externalId));
    }

    @PutMapping("/{externalId}")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(externalId, dto));
    }

    @DeleteMapping("/{externalId}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        categoryService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{externalId}/activate")
    @PreAuthorize("hasAuthority('CATEGORY_ACTIVATE')")
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        categoryService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('CATEGORY_LIST_INACTIVE')")
    public ResponseEntity<List<CategoryResponseDTO>> getInactive() {
        return ResponseEntity.ok(categoryService.getInactive());
    }
}
