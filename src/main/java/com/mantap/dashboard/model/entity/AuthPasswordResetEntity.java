package com.mantap.dashboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUTH_PASSWORD_RESET")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthPasswordResetEntity {

    @Id
    @Column(name = "RESET_TOKEN_ID")
    private String resetTokenId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "TOKEN_HASH", nullable = false)
    private String tokenHash;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "USED_AT")
    private LocalDateTime usedAt;
}