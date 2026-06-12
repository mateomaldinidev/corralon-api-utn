package com.utn.corralon.features.auth;

public record AuthResponse(
        String token,
        String refreshToken) {
}
