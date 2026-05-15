package com.utn.corralon.features.offer_product.repository;

import com.utn.corralon.features.offer_product.entity.OfferProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferProductRepository extends JpaRepository<OfferProductEntity, Long> {
}
