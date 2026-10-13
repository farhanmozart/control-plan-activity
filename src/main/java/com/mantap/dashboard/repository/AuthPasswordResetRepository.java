package com.mantap.dashboard.repository;

import com.mantap.dashboard.model.entity.AuthPasswordResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthPasswordResetRepository extends JpaRepository<AuthPasswordResetEntity, String> {
    List<AuthPasswordResetEntity> findByUserIdAndUsedAtIsNull(String userId);
    Optional<AuthPasswordResetEntity> findByTokenHash(String tokenHash);
}