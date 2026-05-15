package com.utn.corralon.features.productVariant.controller;

import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.service.ProductVariantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ProductVariantResponseDTO> getById(@PathVariable UUID externalId) {
        return ResponseEntity.ok(productVariantService.getById(externalId));
    }

    @GetMapping
    public ResponseEntity<List<ProductVariantResponseDTO>> getAll() {
        return ResponseEntity.ok(productVariantService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> create(@RequestBody ProductVariantRequestDTO productVariantRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productVariantService.create(productVariantRequestDTO));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<ProductVariantResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody ProductVariantRequestDTO productVariantRequestDTO) {
        return ResponseEntity.ok(
                productVariantService.update(externalId, productVariantRequestDTO));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        productVariantService.delete(externalId);
        return ResponseEntity.noContent().build();
    }
}
