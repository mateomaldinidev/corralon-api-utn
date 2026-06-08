package com.utn.corralon.features.notifications.dto;

import com.utn.corralon.features.notifications.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {
    private UUID externalId;
    private UUID userId;
    private String message;
    private LocalDateTime createdAt;
    private Boolean read;
    private NotificationType type;
}