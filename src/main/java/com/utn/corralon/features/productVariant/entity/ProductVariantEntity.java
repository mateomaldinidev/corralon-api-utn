package com.utn.corralon.features.productVariant.entity;

import com.utn.corralon.features.product.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_variants")
public class ProductVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    @UuidGenerator
    private UUID externalId = UUID.randomUUID();

    @Column(name = "attribute", nullable = false)
    private String attribute;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "whole_sale_price", nullable = false)
    private BigDecimal wholesalePrice;

    @Column(name = "wholesale_min_qty", nullable = false) // Nombre de columna más descriptivo
    private Integer wholesaleMinQty; // Tipo Integer para cantidad

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

}