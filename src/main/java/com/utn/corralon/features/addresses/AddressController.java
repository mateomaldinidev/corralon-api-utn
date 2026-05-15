package com.utn.corralon.features.addresses;

import com.utn.corralon.features.addresses.dto.AddressRequestDTO;
import com.utn.corralon.features.addresses.dto.AddressResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    @PostMapping
    public AddressResponseDTO create(
            @Valid @RequestBody AddressRequestDTO request
    ) {

        return addressService.create(request);
    }

    @GetMapping
    public List<AddressResponseDTO> findAll() {

        return addressService.findAll();
    }

    @GetMapping("/{externalId}")
    public AddressResponseDTO findByExternalId(
            @PathVariable UUID externalId
    ) {

        return addressService.findByExternalId(externalId);
    }

    @PutMapping("/{externalId}")
    public AddressResponseDTO update(
            @PathVariable UUID externalId,
            @Valid @RequestBody AddressRequestDTO request
    ) {

        return addressService.update(
                externalId,
                request
        );
    }

    @DeleteMapping("/{externalId}")
    public void delete(
            @PathVariable UUID externalId
    ) {

        addressService.delete(externalId);
    }
}