package com.utn.corralon.features.address.controller;


import com.utn.corralon.features.address.dto.AddressRequestDTO;
import com.utn.corralon.features.address.dto.AddressResponseDTO;
import com.utn.corralon.features.address.service.IAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final IAddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@RequestBody @Valid AddressRequestDTO dto){
        AddressResponseDTO response = addressService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAll(){
        return ResponseEntity.ok(addressService.getAll());
    }
    @GetMapping("/{externalId}")
    public ResponseEntity<AddressResponseDTO> getByExternalId(@PathVariable UUID externalId){
        return ResponseEntity.ok(addressService.getByExternalId(externalId));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<AddressResponseDTO> update(@PathVariable UUID externalId, @RequestBody @Valid AddressRequestDTO dto){

        return ResponseEntity.ok(addressService.update(externalId, dto));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId){
        addressService.delete(externalId);
        return ResponseEntity.noContent().build();
    }
}
