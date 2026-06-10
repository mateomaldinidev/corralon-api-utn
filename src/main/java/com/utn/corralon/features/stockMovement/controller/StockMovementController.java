package com.utn.corralon.features.stockMovement.controller;

import com.utn.corralon.features.stockMovement.dto.StockMovementResponseDTO;
import com.utn.corralon.features.stockMovement.service.IStockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final IStockMovementService stockMovementService;

    // Ver historial de movimientos de una variante.
    @GetMapping("/variant/{variantId}")
    public ResponseEntity<List<StockMovementResponseDTO>> getMovementsByVariant(@PathVariable UUID variantId) {
        return ResponseEntity.ok(stockMovementService.getMovementsByVariant(variantId));
    }

    // Ver un movimiento de stock por su ID
    @GetMapping("/{externalId}")
    public ResponseEntity<StockMovementResponseDTO> getMovementById(@PathVariable UUID externalId) {
        return ResponseEntity.ok(stockMovementService.getMovementById(externalId));
    }
}
