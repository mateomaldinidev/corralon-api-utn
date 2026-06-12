package com.utn.corralon.features.productVariant.controller;

import com.utn.corralon.features.productVariant.dto.ProductVariantRequestDTO;
import com.utn.corralon.features.productVariant.dto.ProductVariantResponseDTO;
import com.utn.corralon.features.productVariant.service.IProductVariantService;
import com.utn.corralon.features.stockMovement.dto.StockMovementRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {
    private final IProductVariantService productVariantService;

    public ProductVariantController(IProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<ProductVariantResponseDTO> getById(
            @PathVariable UUID externalId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.getById(externalId));
    }

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_CREATE')")
    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> create(
            @Valid
            @RequestBody ProductVariantRequestDTO productVariantRequestDTO)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productVariantService.create(productVariantRequestDTO));
    }

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_UPDATE')")
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

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_DELETE')")
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID externalId)
    {
        productVariantService.delete(externalId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_ACTIVATE')")
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable UUID externalId)
    {
        productVariantService.activate(externalId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_LIST')")
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

    @PreAuthorize("hasAuthority('PRODUCT_VARIANT_LIST_INACTIVE')")
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

    @PreAuthorize("hasAuthority('STOCK_ENTRY')")
    @PostMapping("/stock/entry")
    public ResponseEntity<ProductVariantResponseDTO> registerEntry(
            @RequestBody StockMovementRequestDTO dto)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.registerEntry(dto));
    }

    @PreAuthorize("hasAuthority('STOCK_ADJUSTMENT')")
    @PostMapping("/stock/adjustment")
    public ResponseEntity<ProductVariantResponseDTO> adjustStock(
            @RequestBody StockMovementRequestDTO dto)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.adjustStock(dto));
    }

    @PreAuthorize("hasAuthority('STOCK_READ')")
    @GetMapping("/{externalId}/stock")
    public ResponseEntity<Integer> getAvailableStock(
            @PathVariable UUID externalId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productVariantService.getAvailableStock(externalId));
    }
}
