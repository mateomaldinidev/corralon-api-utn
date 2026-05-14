package com.utn.corralon.features.order;

import com.utn.corralon.features.address.addressEntity;
import com.utn.corralon.features.user.userEntity;
import jakarta.persistence.*;
import lombok.*;

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
public class orderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="externalId",nullable = false,unique = true,updatable = false)
     private UUID externalId= UUID.randomUUID();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private userEntity user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="address_id",nullable = false)
    private addressEntity address;

    @Column(name="total",nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @Column(name="created_at",nullable = false)
    private LocalDateTime created_at;

    @Column(name="active",nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<orderItemEntity> items;



}
