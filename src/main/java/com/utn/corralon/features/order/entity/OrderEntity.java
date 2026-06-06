package com.utn.corralon.features.order.entity;

import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.order.orderEnum.OrderStatus;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "external_id", nullable = false, unique = true, updatable = false)
    private UUID externalId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private UserEntity user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="address_id",nullable = false)
    private AddressEntity address;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (externalId == null) {
            externalId = UUID.randomUUID();
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }

    public void addItem(OrderItemEntity item) {

        items.add(item);
        item.setOrder(this);
    }


}
