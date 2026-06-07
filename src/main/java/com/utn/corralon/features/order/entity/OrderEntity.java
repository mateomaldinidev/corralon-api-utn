package com.utn.corralon.features.order.entity;

import com.utn.corralon.features.address.entity.AddressEntity;
import com.utn.corralon.features.order.OrderStatus;
import com.utn.corralon.features.orderItem.entity.OrderItemEntity;
import com.utn.corralon.features.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Column(name="externalId",nullable = false,unique = true,updatable = false)
    @UuidGenerator
     private UUID externalId= UUID.randomUUID();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private UserEntity user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="address_id",nullable = false)
    private AddressEntity address;

    @Column(name="total",nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;



}
