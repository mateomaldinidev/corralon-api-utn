package com.utn.corralon.features.product.controller;


import com.utn.corralon.features.product.dto.ProductDeleteResponseDTO;
import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.service.IProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    // GET BY ID
    @GetMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> getById(
            @PathVariable UUID externalId) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getById(externalId));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid
            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(productRequestDTO));
    }

    // UPDATE
    @PutMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.update(externalId, productRequestDTO)
        );
    }

    // LOGICAL DELETE
    @DeleteMapping("/{externalId}")
    public ResponseEntity<ProductDeleteResponseDTO> delete(
            @PathVariable UUID externalId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.delete(externalId));
    }

    // ACTIVATE
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable UUID externalId) {
        productService.activate(externalId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // SEARCH ACTIVE PRODUCTS
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId
    ) {
        return ResponseEntity
                .status(200)
                .body(productService.search(name, supplierId, categoryId, brandId));
    }

    // SEARCH INACTIVE PRODUCTS
    @GetMapping("/inactive")
    public ResponseEntity<List<ProductResponseDTO>> getInactive(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getInactive(
                                name,
                                supplierId,
                                categoryId,
                                brandId));
    }

}
