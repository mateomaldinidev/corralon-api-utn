package com.utn.corralon.features.user;

import com.utn.corralon.features.addresses.addressEntity;
import com.utn.corralon.features.role.roleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.security.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class userEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
    private UUID externalId = UUID.randomUUID();

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    @Column(name = "role", nullable = false)
    private roleEntity role;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "user")
    private List<addressEntity> addresses;
}
