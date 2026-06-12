package com.utn.corralon.features.productVariant.controller;

import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.service.IProductVariantService;
import com.utn.corralon.features.stockMovement.dto.StockMovementRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product-variants")
public class ProductVariantController {
    private final IProductVariantService productVariantService;

    public ProductVariantController(IProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    //GET BY ID
    @GetMapping("/{externalId}")
    public ResponseEntity<ProductVariantResponseDTO> getById(
            @PathVariable UUID externalId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.getById(externalId));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> create(
            @Valid
            @RequestBody ProductVariantRequestDTO productVariantRequestDTO)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productVariantService.create(productVariantRequestDTO));
    }

    // UPDATE
    @PutMapping("/{externalId}")
    public ResponseEntity<ProductVariantResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody ProductVariantRequestDTO productVariantRequestDTO)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.update(
                        externalId,
                        productVariantRequestDTO
                ));
    }

    // LOGICAL DELETE
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID externalId)
    {
        productVariantService.delete(externalId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // ACTIVATE
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable UUID externalId)
    {
        productVariantService.activate(externalId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // SEARCH ACTIVE VARIANTS
    @GetMapping("/search")
    public ResponseEntity<List<ProductVariantResponseDTO>> search(
            @RequestParam(required = false) String attribute,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId,
            @RequestParam(required = false) String productName)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.search(
                        attribute,
                        minPrice,
                        maxPrice,
                        minStock,
                        productId,
                        categoryId,
                        brandId,
                        productName
                ));
    }

    // SEARCH INACTIVE VARIANTS
    @GetMapping("/inactive")
    public ResponseEntity<List<ProductVariantResponseDTO>> getInactive(
            @RequestParam(required = false) String attribute,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID brandId,
            @RequestParam(required = false) String productName)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.getInactive(
                        attribute,
                        minPrice,
                        maxPrice,
                        minStock,
                        productId,
                        categoryId,
                        brandId,
                        productName
                ));
    }

    // STOCK ENTRY
    @PostMapping("/stock/entry")
    public ResponseEntity<ProductVariantResponseDTO> registerEntry(
            @RequestBody StockMovementRequestDTO dto)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.registerEntry(dto));
    }

    // STOCK ADJUSTMENT
    @PostMapping("/stock/adjustment")
    public ResponseEntity<ProductVariantResponseDTO> adjustStock(
            @RequestBody StockMovementRequestDTO dto)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.adjustStock(dto));
    }

    // AVAILABLE STOCK
    @GetMapping("/{externalId}/stock")
    public ResponseEntity<Integer> getAvailableStock(
            @PathVariable UUID externalId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.getAvailableStock(externalId));
    }












}
