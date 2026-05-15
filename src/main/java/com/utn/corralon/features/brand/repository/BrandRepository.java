package com.utn.corralon.features.brand.repository;

import com.utn.corralon.features.brand.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
}
