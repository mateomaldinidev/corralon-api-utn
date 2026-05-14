package com.utn.corralon.features.product;

import com.utn.corralon.features.productVariant.productVariantEntity;
import com.utn.corralon.features.supplier.supplierEntity;
import com.utn.corralon.features.categories.categoriesEntity;
import com.utn.corralon.features.brand.brandEntity;
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
    private List<productVariantEntity> productVariants;

    @ManyToOne()
    @JoinColumn(name = "supplier_id", nullable = false)
    private supplierEntity supplier;

    @ManyToOne()
    @JoinColumn(name = "categorie_id", nullable = false)
    private categoriesEntity categorie;

    @ManyToOne()
    @JoinColumn(name = "brand_id", nullable = false)
    private brandEntity brand;

    public boolean getActive() {
        return active;
    }
}
