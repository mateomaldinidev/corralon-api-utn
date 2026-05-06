package com.utn.corralon.features.cart;

import com.utn.corralon.features.products.productVariantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="cart_items")
public class cartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="externalId",nullable = false,unique = true,updatable = false)
    private UUID externalId= UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id",nullable = false)
    private cartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_variant_id",nullable = false)
    private productVariantEntity productVariant;

    @Column(name="quantity", nullable = false)
    private Integer quantity;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt; //me parece que es importante para auditar


}
