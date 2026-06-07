package com.utn.corralon.features.stockMovement.entity;

import com.utn.corralon.features.productVariant.entity.ProductVariantEntity;
import com.utn.corralon.features.stockMovement.enums.StockMovementType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "stockMovements")
public class StockMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @Column(name = "externalId", nullable = false, unique = true)
    private UUID externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "stockMovementType", nullable = false)
    private StockMovementType type;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "movementDate", nullable = false)
    private LocalDateTime movementDate;

    @Column(name = "reason", length = 500)
    private String reason;

    @ManyToMany
    @JoinColumn(name = "varianTid", nullable = false)
    private ProductVariantEntity variant;

}
