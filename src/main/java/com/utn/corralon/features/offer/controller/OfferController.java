package com.utn.corralon.features.offer.controller;

import com.utn.corralon.features.offer.dto.OfferRequestDTO;
import com.utn.corralon.features.offer.dto.OfferResponseDTO;
import com.utn.corralon.features.offer.service.IOfferService;
import com.utn.corralon.features.offer_product.dto.OfferProductRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final IOfferService offerService;

    // Crear una nueva oferta
    @PostMapping
    public ResponseEntity<OfferResponseDTO> create(@RequestBody @Valid OfferRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.create(dto));
    }

    // Agregar un producto a una oferta existente
    @PostMapping("/{offerExternalId}/products")
    public ResponseEntity<OfferResponseDTO> addProduct(
            @PathVariable UUID offerExternalId,
            @RequestBody @Valid OfferProductRequestDTO dto) {
        return ResponseEntity.ok(offerService.addProductToOffer(dto));
    }

    // Sacar un producto de una oferta
    @DeleteMapping("/{offerExternalId}/products/{productVariantExternalId}")
    public ResponseEntity<OfferResponseDTO> removeProduct(
            @PathVariable UUID offerExternalId,
            @PathVariable UUID productVariantExternalId) {
        return ResponseEntity.ok(offerService.removeProductFromOffer(offerExternalId, productVariantExternalId));
    }

    // Buscar una oferta por su ID
    @GetMapping("/{externalId}")
    public ResponseEntity<OfferResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(offerService.getByExternalId(externalId));
    }

    // Listar todas las ofertas activas
    @GetMapping
    public ResponseEntity<List<OfferResponseDTO>> getAllActive() {
        return ResponseEntity.ok(offerService.getAllActive());
    }

    // Listar todas las ofertas inactivas
    @GetMapping("/inactive")
    public ResponseEntity<List<OfferResponseDTO>> getAllInactive() {
        return ResponseEntity.ok(offerService.getAllInactive());
    }

    // Activar una oferta
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        offerService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    // Desactivar una oferta
    @PatchMapping("/{externalId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID externalId) {
        offerService.deactivate(externalId);
        return ResponseEntity.noContent().build();
    }
}
