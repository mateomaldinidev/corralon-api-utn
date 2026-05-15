package com.utn.corralon.features.payment.entity;


import com.utn.corralon.features.order.entity.OrderEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name="amount",nullable = false,precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;




}



