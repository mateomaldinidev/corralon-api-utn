package com.utn.corralon.features.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<orderEntity, Long> {

    Optional<orderEntity> findByExternalId(UUID externalId);
}
