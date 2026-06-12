package com.utn.corralon.features.notifications.controller;

import com.utn.corralon.features.notifications.dto.NotificationRequestDTO;
import com.utn.corralon.features.notifications.dto.NotificationResponseDTO;
import com.utn.corralon.features.notifications.service.INotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PreAuthorize("hasAuthority('NOTIFICATION_CREATE')")
    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestBody @Valid NotificationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(dto));
    }

    @PreAuthorize("hasAuthority('NOTIFICATION_READ_BY_USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getAllByUserId(userId));
    }

    @PreAuthorize("hasAuthority('NOTIFICATION_MARK_AS_READ')")
    @PatchMapping("/{notificationExternalId}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable UUID notificationExternalId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationExternalId));
    }

    @PreAuthorize("hasAuthority('NOTIFICATION_DELETE')")
    @DeleteMapping("/{notificationExternalId}")
    public ResponseEntity<Void> delete(@PathVariable UUID notificationExternalId) {
        notificationService.delete(notificationExternalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('NOTIFICATION_MARK_ALL_AS_READ')")
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsReadByUserId(@PathVariable UUID userId) {
        notificationService.markAllAsReadByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
