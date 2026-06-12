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

    @GetMapping("/variant/{variantId}")
    @PreAuthorize("hasAuthority('STOCK_MOVEMENT_LIST_BY_VARIANT')")
    public ResponseEntity<List<StockMovementResponseDTO>> getMovementsByVariant(@PathVariable UUID variantId) {
        return ResponseEntity.ok(stockMovementService.getMovementsByVariant(variantId));
    }

    @GetMapping("/{externalId}")
    @PreAuthorize("hasAuthority('STOCK_MOVEMENT_READ')")
    public ResponseEntity<StockMovementResponseDTO> getMovementById(@PathVariable UUID externalId) {
        return ResponseEntity.ok(stockMovementService.getMovementById(externalId));
    }
}
