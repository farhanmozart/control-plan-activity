package com.mantap.dashboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUTH_REFRESH_TOKEN")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    private String refreshTokenId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "TOKEN_HASH", nullable = false, unique = true)
    private String tokenHash;

    @Column(name = "TOKEN_VERSION", nullable = false)
    private Long tokenVersion;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "REVOKED_AT")
    private LocalDateTime revokedAt;

    @Column(name = "REPLACED_BY_TOKEN_ID")
    private String replacedByTokenId;
}