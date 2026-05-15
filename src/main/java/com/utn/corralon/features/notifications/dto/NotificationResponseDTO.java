package com.utn.corralon.features.notifications.dto;

import com.utn.corralon.features.notifications.entity.NotificationType;
import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
        UUID externalId,
        UUID userExternalId,
        String message,
        LocalDateTime createdAt,
        Boolean read,
        NotificationType type
) { }