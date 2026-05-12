package com.utn.corralon.features.notifications;

import com.utn.corralon.features.users.userEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class notificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "externalId", nullable = false, unique = true, updatable = false)
        private UUID externalId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private userEntity user;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="read",nullable = false)
    private Boolean read;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private notificationType type;



}
