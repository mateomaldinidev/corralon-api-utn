package com.utn.corralon.features.product.controller;


import com.utn.corralon.features.product.dto.ProductDeleteResponseDTO;
import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.service.IProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    // GET BY ID
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> getById(
            @PathVariable UUID externalId) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getById(externalId));
    }

    // CREATE
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid
            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(productRequestDTO));
    }

    // UPDATE
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
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
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @DeleteMapping("/{externalId}")
    public ResponseEntity<ProductDeleteResponseDTO> delete(
            @PathVariable UUID externalId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.delete(externalId));
    }

    // ACTIVATE
    @PreAuthorize("hasAuthority('PRODUCT_ACTIVATE')")
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable UUID externalId) {
        productService.activate(externalId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // SEARCH ACTIVE PRODUCTS
    @PreAuthorize("hasAuthority('PRODUCT_LIST')")
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
    @PreAuthorize("hasAuthority('PRODUCT_LIST_INACTIVE')")
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
