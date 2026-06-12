package com.utn.corralon.features.user.controller;

import com.utn.corralon.features.user.dto.UserRequestDTO;
import com.utn.corralon.features.user.dto.UserResponseDTO;
import com.utn.corralon.features.user.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private final IUserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_LIST')")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{externalId}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<UserResponseDTO> getByExternalId(@PathVariable UUID externalId) {
        return ResponseEntity.ok(userService.getByExternalId(externalId));
    }

    @PutMapping("/{externalId}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(userService.update(externalId, dto));
    }

    @DeleteMapping("/{externalId}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable UUID externalId) {
        userService.delete(externalId);
        return ResponseEntity.noContent().build();
    }
}
