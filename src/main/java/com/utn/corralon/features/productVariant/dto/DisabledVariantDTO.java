package com.utn.corralon.features.productVariant.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DisabledVariantDTO {
    private UUID externalId;
    private String attribute;
}
