package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.RefreshTokenResult;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.entity.tasks.RefreshTokenEntity;
import com.mantap.dashboard.repository.RefreshTokenRepository;
import com.mantap.dashboard.util.RefreshTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenUtil refreshTokenUtil;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    public RefreshTokenResult create(UsersEntity user) {

        String rawToken = refreshTokenUtil.generateToken();

        String tokenHash = refreshTokenUtil.hashToken(rawToken);

        LocalDateTime now = LocalDateTime.now();

        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                        .refreshTokenId(UUID.randomUUID().toString())
                        .userId(user.getUserId())
                        .tokenHash(tokenHash)
                        .tokenVersion(Optional.ofNullable(user.getTokenVersion()).orElse(0L))
                        .createdAt(now)
                        .expiresAt(now.plus(Duration.ofMillis(refreshExpiration)))
                        .build();

        refreshTokenRepository.save(entity);
        return new RefreshTokenResult(rawToken, entity);
    }
}