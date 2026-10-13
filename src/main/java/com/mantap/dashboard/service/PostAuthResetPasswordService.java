package com.mantap.dashboard.service;

import com.mantap.dashboard.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.AuthPasswordResetEntity;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.entity.tasks.RefreshTokenEntity;
import com.mantap.dashboard.model.request.PostAuthResetPasswordRequest;
import com.mantap.dashboard.repository.AuthPasswordResetRepository;
import com.mantap.dashboard.repository.RefreshTokenRepository;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.PasswordResetTokenUtil;
import com.mantap.dashboard.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.RESET_PASSWORD_SUCCESS_MESSAGE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;

@Service
@RequiredArgsConstructor
public class PostAuthResetPasswordService extends ResponseUtil {

    private final UsersRepository usersRepository;
    private final AuthPasswordResetRepository authPasswordResetRepository;
    private final PasswordResetTokenUtil passwordResetTokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponse<Void> execute(PostAuthResetPasswordRequest input) {

        String tokenHash = passwordResetTokenUtil.hashToken(input.getResetToken());
        AuthPasswordResetEntity resetToken = authPasswordResetRepository.findByTokenHash(tokenHash)
                .orElseThrow(this::invalidResetToken);

        validateResetToken(resetToken);
        UsersEntity user = usersRepository.findById(resetToken.getUserId()).orElseThrow(this::invalidResetToken);
        validatePassword(user, input);
        updatePassword(user, input.getNewPassword());
        revokeRefreshTokens(user.getUserId());
        resetToken.setUsedAt(LocalDateTime.now());
        return ResponseUtil.success(SUCCESS_CODE, RESET_PASSWORD_SUCCESS_MESSAGE, null);
    }

    private void validateResetToken(AuthPasswordResetEntity resetToken) {
        if (resetToken.getUsedAt() != null) {
            throw invalidResetToken();
        }
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw invalidResetToken();
        }
    }

    private void validatePassword(UsersEntity user, PostAuthResetPasswordRequest input) {
        if (!input.getNewPassword().equals(input.getConfirmPassword())) {
            throw new BusinessException(ERROR_CODE_10011, ERROR_TITLE_10011, ERROR_MESSAGE_10011);
        }

        if (passwordEncoder.matches(input.getNewPassword(), user.getPassword())) {
            throw new BusinessException(ERROR_CODE_10012, ERROR_TITLE_10012, ERROR_MESSAGE_10012);
        }
    }

    private void updatePassword(UsersEntity user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        long currentTokenVersion = user.getTokenVersion() == null ? 0L : user.getTokenVersion();
        user.setTokenVersion(currentTokenVersion + 1);

        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
    }

    private void revokeRefreshTokens(String userId) {
        List<RefreshTokenEntity> tokens = refreshTokenRepository.findByUserIdAndRevokedAtIsNull(userId);
        LocalDateTime now = LocalDateTime.now();
        tokens.forEach(token -> token.setRevokedAt(now));
    }

    private BusinessException invalidResetToken() {
        return new BusinessException(ERROR_CODE_10013, ERROR_TITLE_10013, ERROR_MESSAGE_10013);
    }
}