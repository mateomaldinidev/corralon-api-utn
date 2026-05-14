package com.utn.corralon.features.supplier;

import com.utn.corralon.features.product.ProductEntity;
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
@Table(name = "suppliers")
public class supplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "supplier")
    private List<ProductEntity> products;
}
