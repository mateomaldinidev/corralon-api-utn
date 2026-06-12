package com.utn.corralon.features.auth;


public record AuthRequest(
        String username,
        String password
) {
}
