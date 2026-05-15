package com.utn.corralon.features.productVariant.exception;

import java.util.UUID;

public class ProductVariantNotFoundException extends RuntimeException {
    public ProductVariantNotFoundException(UUID externalId) {
        super("Product variant not found by id: " + externalId);
    }
}
