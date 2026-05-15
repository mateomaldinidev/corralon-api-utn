package com.utn.corralon.features.product.entity;

import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.supplier.entity.SupplierEntity;
import com.utn.corralon.features.category.entity.CategoryEntity;
import com.utn.corralon.features.brand.entity.BrandEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "product")
    private List<ProductVariantEntity> productVariants;

    @ManyToOne()
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne()
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;
}
