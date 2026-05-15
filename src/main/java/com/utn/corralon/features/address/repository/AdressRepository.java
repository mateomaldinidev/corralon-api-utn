package com.utn.corralon.features.address.repository;

import com.utn.corralon.features.address.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<AddressEntity, Long> {
}
