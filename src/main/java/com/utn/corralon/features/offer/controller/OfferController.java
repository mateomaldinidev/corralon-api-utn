package com.utn.corralon.features.offer.controller;

import com.utn.corralon.features.offer.dto.OfferRequestDTO;
import com.utn.corralon.features.offer.dto.OfferResponseDTO;
import com.utn.corralon.features.offer.service.IOfferService;
import com.utn.corralon.features.offer_product.dto.OfferProductRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final IOfferService offerService;

    @PreAuthorize("hasAuthority('OFFER_CREATE')")
    @PostMapping
    public ResponseEntity<OfferResponseDTO> create(@RequestBody @Valid OfferRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.create(dto));
    }

    @PreAuthorize("hasAuthority('OFFER_ADD_PRODUCT')")
    @PostMapping("/{offerExternalId}/products")
    public ResponseEntity<OfferResponseDTO> addProduct(
            @PathVariable UUID offerExternalId,
            @RequestBody @Valid OfferProductRequestDTO dto) {
        return ResponseEntity.ok(offerService.addProductToOffer(dto));
    }

    @PreAuthorize("hasAuthority('OFFER_REMOVE_PRODUCT')")
    @DeleteMapping("/{offerExternalId}/products/{productVariantExternalId}")
    public ResponseEntity<OfferResponseDTO> removeProduct(
            @PathVariable UUID offerExternalId,
            @PathVariable UUID productVariantExternalId) {
        return ResponseEntity.ok(offerService.removeProductFromOffer(offerExternalId, productVariantExternalId));
    }

    @PreAuthorize("hasAuthority('OFFER_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<OfferResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(offerService.getByExternalId(externalId));
    }

    @PreAuthorize("hasAuthority('OFFER_LIST')")
    @GetMapping
    public ResponseEntity<List<OfferResponseDTO>> getAllActive() {
        return ResponseEntity.ok(offerService.getAllActive());
    }

    @PreAuthorize("hasAuthority('OFFER_LIST_INACTIVE')")
    @GetMapping("/inactive")
    public ResponseEntity<List<OfferResponseDTO>> getAllInactive() {
        return ResponseEntity.ok(offerService.getAllInactive());
    }

    @PreAuthorize("hasAuthority('OFFER_ACTIVATE')")
    @PatchMapping("/{externalId}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID externalId) {
        offerService.activate(externalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('OFFER_DEACTIVATE')")
    @PatchMapping("/{externalId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID externalId) {
        offerService.deactivate(externalId);
        return ResponseEntity.noContent().build();
    }
}
