package com.utn.corralon.features.notifications.dto;

import com.utn.corralon.features.notifications.notificationType;
import java.time.LocalDateTime;
import java.util.UUID;

public record notificationResponseDTO(
        UUID externalId,
        UUID userExternalId,
        String message,
        LocalDateTime createdAt,
        Boolean read,
        notificationType type
) { }