package com.utn.corralon.features.product.controller;


import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID externalId) {
        return  ResponseEntity.ok(productService.getById(externalId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(productRequestDTO));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(
                productService.update(externalId, productRequestDTO)
        );
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        productService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

}
