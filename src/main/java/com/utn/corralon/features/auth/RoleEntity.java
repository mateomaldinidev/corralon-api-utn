package com.utn.corralon.features.auth;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Roles role;

    @ManyToMany
    @JoinTable(
            name = "rolePermits",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permitsId"))
    private Set<PermitEntity> permits = new HashSet<>();

    public RoleEntity(Roles name) {
        this.role = name;
    }

    public void addPermits(PermitEntity permit) {
        this.permits.add(permit);
    }
}
