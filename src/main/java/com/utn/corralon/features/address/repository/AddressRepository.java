package com.utn.corralon.features.address.repository;

import com.utn.corralon.features.address.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByExternalId(UUID externalId);
    List<AddressEntity> findAllByUserExternalId(UUID userExternalId);// busca todas las direcciones que pertenece a un usuario en especifico
}
