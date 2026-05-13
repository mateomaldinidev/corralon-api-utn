package com.utn.corralon.features.brands;

import jakarta.persistence.*;
import com.utn.corralon.features.product.ProductEntity;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "brands")
public class brandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "brand")
    private List<ProductEntity> products;

    public boolean isActive(){
        return true;
    }
}
