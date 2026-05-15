package com.utn.corralon.features.notifications.dto;

import com.utn.corralon.features.notifications.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class NotificationRequestDTO {
    @NotNull
    private UUID userId;
    @NotBlank
    private String message;
    @NotNull
    private NotificationType type;
}
