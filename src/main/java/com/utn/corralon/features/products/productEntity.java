package com.utn.corralon.features.products;

import com.utn.corralon.features.suppliers.supplierEntity;
import com.utn.corralon.features.categories.categorieEntity;
import com.utn.corralon.features.brands.brandEntity;
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
public class productEntity {

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
    private categorieEntity categorie;

    @ManyToOne()
    @JoinColumn(name = "brand_id", nullable = false)
    private brandEntity brand;
}
