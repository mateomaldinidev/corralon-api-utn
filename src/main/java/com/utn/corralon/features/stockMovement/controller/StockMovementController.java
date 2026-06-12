package com.utn.corralon.features.stockMovement.controller;

import com.utn.corralon.features.stockMovement.dto.StockMovementResponseDTO;
import com.utn.corralon.features.stockMovement.service.IStockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final IStockMovementService stockMovementService;

    @PreAuthorize("hasAuthority('STOCK_MOVEMENT_LIST_BY_VARIANT')")
    @GetMapping("/variant/{variantId}")
    public ResponseEntity<List<StockMovementResponseDTO>> getMovementsByVariant(@PathVariable UUID variantId) {
        return ResponseEntity.ok(stockMovementService.getMovementsByVariant(variantId));
    }

    @PreAuthorize("hasAuthority('STOCK_MOVEMENT_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<StockMovementResponseDTO> getMovementById(@PathVariable UUID externalId) {
        return ResponseEntity.ok(stockMovementService.getMovementById(externalId));
    }
}
