package com.utn.corralon.features.notifications.dto;

import com.utn.corralon.features.notifications.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class NotificationRequestDTO {
    @NotNull(message = "User ID cannot be null")
    private UUID userId;
    @NotBlank(message = "Message cannot be blank")
    private String message;
    @NotNull(message = "Notification type cannot be null")
    private NotificationType type;
}
