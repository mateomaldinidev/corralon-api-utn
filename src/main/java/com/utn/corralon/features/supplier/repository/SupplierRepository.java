package com.utn.corralon.features.supplier.repository;

import com.utn.corralon.features.supplier.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface SupplierRepository extends JpaRepository<SupplierRepository, Long> {

    Optional<SupplierEntity> findByExternalId(UUID externalId);
}
