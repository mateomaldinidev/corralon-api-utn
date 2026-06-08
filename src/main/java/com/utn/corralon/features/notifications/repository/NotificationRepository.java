package com.utn.corralon.features.notifications.repository;

import com.utn.corralon.features.notifications.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Optional<NotificationEntity> findByExternalId(UUID externalId);
    List<NotificationEntity> findAllByUser_ExternalIdOrderByCreatedAtDesc(UUID userExternalId);
    void deleteAllByUser_ExternalId(UUID userExternalId); // Para marcar todas como leidas o eliminarlas
}
