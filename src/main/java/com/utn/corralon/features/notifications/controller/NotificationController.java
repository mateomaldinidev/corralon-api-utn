package com.utn.corralon.features.notifications.controller;

import com.utn.corralon.features.notifications.dto.NotificationRequestDTO;
import com.utn.corralon.features.notifications.dto.NotificationResponseDTO;
import com.utn.corralon.features.notifications.service.INotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestBody @Valid NotificationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getAllByUserId(userId));
    }

    // Endpoint para marcar una notificacion específica como leída
    @PatchMapping("/{notificationExternalId}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable UUID notificationExternalId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationExternalId));
    }

    @DeleteMapping("/{notificationExternalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID notificationExternalId) {
        notificationService.delete(notificationExternalId);
        return ResponseEntity.noContent().build();
    }

    // marcar todas las notificaciones de un usuario como leidas
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsReadByUserId(@PathVariable UUID userId) {
        notificationService.markAllAsReadByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}