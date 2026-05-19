package com.utn.corralon.features.product.controller;


import com.utn.corralon.features.product.dto.ProductRequestDTO;
import com.utn.corralon.features.product.dto.ProductResponseDTO;
import com.utn.corralon.features.product.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> getById(@Valid @PathVariable UUID externalId) {
        return  ResponseEntity.ok(productService.getById(externalId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(productRequestDTO));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<ProductResponseDTO> update(
            @Valid
            @PathVariable UUID externalId,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(
                productService.update(externalId, productRequestDTO)
        );
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@Valid@PathVariable UUID externalId) {
        productService.delete(externalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId
    ) {
        return ResponseEntity.ok(productService.search(name, active, supplierId, categoryId, brandId));
    }


    //tengo que agrefar rango precio,paginación,sorting
}
