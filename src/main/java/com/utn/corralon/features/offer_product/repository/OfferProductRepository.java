package com.utn.corralon.features.offer_product.repository;

import com.utn.corralon.features.offer_product.entity.OfferProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferProductRepository extends JpaRepository<OfferProductEntity, Long> {
    Optional<OfferProductEntity> findByOfferExternalIdAndProductVariantExternalId(
            UUID offerExternalId, UUID productVariantExternalId);
    boolean existsByOfferExternalIdAndProductVariantExternalId(
            UUID offerExternalId, UUID productVariantExternalId);
    List<OfferProductEntity> findByOfferExternalId(UUID offerExternalId);
}
