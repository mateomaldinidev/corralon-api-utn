package com.utn.corralon.features.productVariant.repository;

import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {

    Optional<ProductVariantEntity> findByExternalId(UUID externalId);
}
