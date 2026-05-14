package com.utn.corralon.features.notifications.mapper;

import com.utn.corralon.features.notifications.dto.notificationResponseDTO;
import com.utn.corralon.features.notifications.notificationEntity;
import jakarta.validation.constraints.NotNull;

public class notificationMapper {
    public static notificationResponseDTO toDTO (@NotNull notificationEntity notification){
        return new notificationResponseDTO(
                notification.getExternalId(),
                notification.getUser().getExternalId(),
                notification.getMessage(),
                notification.getCreatedAt(),
                notification.getRead(),
                notification.getType()
        );

    }
}
