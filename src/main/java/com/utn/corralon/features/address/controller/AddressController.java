package com.utn.corralon.features.address.controller;

import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import com.utn.corralon.features.address.service.IAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    @PreAuthorize("hasAuthority('ADDRESS_CREATE')")
    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@RequestBody @Valid AddressRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(dto));
    }

    @PreAuthorize("hasAuthority('ADDRESS_LIST')")
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @PreAuthorize("hasAuthority('ADDRESS_READ')")
    @GetMapping("/{externalId}")
    public ResponseEntity<AddressResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(addressService.getByExternalId(externalId));
    }

    @PreAuthorize("hasAuthority('ADDRESS_UPDATE')")
    @PutMapping("/{externalId}")
    public ResponseEntity<AddressResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.update(externalId, dto));
    }

    @PreAuthorize("hasAuthority('ADDRESS_DELETE')")
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId, @RequestParam UUID userExternalId) {
        addressService.delete(externalId, userExternalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADDRESS_LIST_BY_USER')")
    @GetMapping("/user/{userExternalId}")
    public ResponseEntity<List<AddressResponseDTO>> getAllByUserExternalId(@PathVariable UUID userExternalId) {
        return ResponseEntity.ok(addressService.getAllByUserExternalId(userExternalId));
    }
}
