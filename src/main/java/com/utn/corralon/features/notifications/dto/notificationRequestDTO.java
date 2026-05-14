package com.utn.corralon.features.notifications.dto;

import com.utn.corralon.features.notifications.notificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class notificationRequestDTO {
    @NotNull
    private UUID userId;
    @NotBlank
    private String message;
    @NotNull
    private notificationType type;
}
