package com.utn.corralon.features.order;

import com.utn.corralon.features.productVariant.productVariantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="oder_items")
public class orderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="externalId",nullable = false,unique = true,updatable = false)
    private UUID externalId= UUID.randomUUID();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="order_id",nullable = false)
    private orderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_variant_id",nullable = false)
    private productVariantEntity productVariant;

    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name="unit_price",nullable = false,precision = 19, scale = 2)
    private BigDecimal unit_price;

    @Column(name="subtotal",nullable = false,precision = 19, scale = 2)
    private BigDecimal subtotal; // SE LO SUMAMOS AUNQUE NO ESTE EN EL DER PORQUE ES IMPORTANTE YA QUE HAY QUE TENER UN HISTORIAL DE PRECIOS
}
