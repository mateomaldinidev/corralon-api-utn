package com.utn.corralon.features.user.repository;

import com.utn.corralon.features.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar List
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByExternalId(UUID externalId);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByExternalId(UUID externalId);

    // Nuevos métodos para consistencia con el campo 'active'
    List<UserEntity> findAllByActiveTrue();
    Optional<UserEntity> findByExternalIdAndActiveTrue(UUID externalId);
    List<UserEntity> findAllByActiveFalse();
}