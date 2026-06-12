package com.utn.corralon.features.notifications.service;

import com.utn.corralon.features.notifications.dto.NotificationRequestDTO;
import com.utn.corralon.features.notifications.dto.NotificationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface INotificationService {
    NotificationResponseDTO create(NotificationRequestDTO dto);
    List<NotificationResponseDTO> getAllByUserId(UUID userId);
    List<NotificationResponseDTO> getAll(); // Nuevo método para obtener todas las notificaciones
    NotificationResponseDTO markAsRead(UUID notificationExternalId);
    void delete(UUID notificationExternalId);
    void markAllAsReadByUserId(UUID userId);
}