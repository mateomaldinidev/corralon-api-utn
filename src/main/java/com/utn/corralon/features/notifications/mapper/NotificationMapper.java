package com.utn.corralon.features.notifications.mapper;

import com.utn.corralon.features.notifications.dto.NotificationResponseDTO;
import com.utn.corralon.features.notifications.entity.NotificationEntity;
import jakarta.validation.constraints.NotNull;

public class NotificationMapper {
    public static NotificationResponseDTO toDTO (@NotNull NotificationEntity notification){
        return new NotificationResponseDTO(
                notification.getExternalId(),
                notification.getUser().getExternalId(),
                notification.getMessage(),
                notification.getCreatedAt(),
                notification.getRead(),
                notification.getType()
        );

    }
}
