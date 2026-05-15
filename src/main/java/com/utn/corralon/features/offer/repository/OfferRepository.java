package com.utn.corralon.features.offer.repository;

import com.utn.corralon.features.offer.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
}
