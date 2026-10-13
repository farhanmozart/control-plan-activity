package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.AuthPasswordResetEntity;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.request.PostAuthForgotPasswordRequest;
import com.mantap.dashboard.repository.AuthPasswordResetRepository;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.PasswordResetTokenUtil;
import com.mantap.dashboard.util.ResponseUtil;
import com.mantap.dashboard.util.enumeration.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mantap.dashboard.util.constant.ResponseConstant.FORGOT_PASSWORD_SUCCESS_MESSAGE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;

@Service
@RequiredArgsConstructor
public class PostAuthForgotPasswordService extends ResponseUtil {
    private final UsersRepository usersRepository;
    private final AuthPasswordResetRepository authPasswordResetRepository;
    private final PasswordResetTokenUtil passwordResetTokenUtil;

    @Transactional
    public BaseResponse<Void> execute(PostAuthForgotPasswordRequest input) {
        String identifier = input.getIdentifier().trim();
        Optional<UsersEntity> optionalUser = usersRepository.findByNipOrEmail(identifier, identifier);

        if (optionalUser.isEmpty()) {
            return constructResponse();
        }

        UsersEntity user = optionalUser.get();

        if (!Boolean.TRUE.equals(user.getIsActive()) || user.getStatus() != UserStatus.ACTIVE) {
            return constructResponse();
        }

        revokePreviousTokens(user.getUserId());

        String rawToken = passwordResetTokenUtil.generateToken();
        String tokenHash = passwordResetTokenUtil.hashToken(rawToken);
        LocalDateTime now = LocalDateTime.now();

        AuthPasswordResetEntity entity = AuthPasswordResetEntity.builder()
                .resetTokenId(UUID.randomUUID().toString())
                .userId(user.getUserId())
                .tokenHash(tokenHash)
                .createdAt(now)
                .expiresAt(now.plusMinutes(15))
                .build();
        authPasswordResetRepository.save(entity);
        /*
            * TODO:
            *  kirim via email/notification berisi reset link
        */

        return constructResponse();
    }

    private BaseResponse<Void> constructResponse() {
        return ResponseUtil.success(SUCCESS_CODE, FORGOT_PASSWORD_SUCCESS_MESSAGE, null);
    }

    private void revokePreviousTokens(String userId) {
        List<AuthPasswordResetEntity> tokens = authPasswordResetRepository.findByUserIdAndUsedAtIsNull(userId);
        LocalDateTime now = LocalDateTime.now();
        tokens.forEach(token -> token.setUsedAt(now));
    }
}