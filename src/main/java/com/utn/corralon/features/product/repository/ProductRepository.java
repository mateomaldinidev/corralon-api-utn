package com.utn.corralon.features.product.repository;

import com.utn.corralon.features.brand.entity.BrandEntity;
import com.utn.corralon.features.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    Optional<ProductEntity> findByExternalId(UUID externalId);

    boolean existByNameAndBrand(String name, BrandEntity brand);
}
