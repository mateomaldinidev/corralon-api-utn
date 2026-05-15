package com.utn.corralon.features.addresses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<addressEntity, Long> {

    Optional<addressEntity> findByExternalId(UUID externalId);
}
