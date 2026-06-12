package com.utn.corralon.features.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping
    public String test() {
        return "OK";
    }

    @GetMapping("/test-auth")
    @PreAuthorize("isAuthenticated()")
    public String testAuth() {
        return "OK";
    }
}
