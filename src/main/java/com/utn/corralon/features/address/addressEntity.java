package com.utn.corralon.features.address;

import com.utn.corralon.features.users.userEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class addressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "streetNumber", nullable = false)
    private String streetNumber;
    @Column(name = "floor", nullable = false)
    private String floor;
    @Column(name = "apartmentNumber", nullable = false)
    private String apartmentNumber;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private userEntity user;
}
