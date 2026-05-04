package com.utn.corralon.features.productVariant;

import com.utn.corralon.features.product.productEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_variants")
public class productVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "atrivute", nullable = false)
    private String atrivute;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "whole_sale_price", nullable = false)
    private BigDecimal wholesalePrice;

    @Column(name = "whole_min_stock", nullable = false)
    private BigDecimal wholeMinStock;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private productEntity product;

}
