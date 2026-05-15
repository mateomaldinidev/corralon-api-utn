package com.utn.corralon.features.product.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(UUID externalId) {
        super("Product not found by id: " + externalId);
    }
}
