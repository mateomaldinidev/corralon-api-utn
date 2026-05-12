package com.utn.corralon.features.offers;

import com.utn.corralon.features.productVariant.productVariantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "offer_products")
public class offerProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private offerEntity offer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private productVariantEntity productVariant;

    @Column(name = "discounted_price",nullable = false, precision = 19, scale = 2)
    private BigDecimal discountedPrice;//agregamos esto porque el precio original esta en productVatiant y el precio con descuento es distinto por oferta

}
