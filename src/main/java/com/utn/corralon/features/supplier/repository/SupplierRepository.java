package com.utn.corralon.features.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplierRepository extends JpaRepository<SupplierRepository, Long> {
}
