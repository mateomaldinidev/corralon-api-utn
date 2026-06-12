package com.utn.corralon.features.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierResponseDTO{

    private UUID externalId;

    private String name;

    private String contact;

    private Boolean active;
}
