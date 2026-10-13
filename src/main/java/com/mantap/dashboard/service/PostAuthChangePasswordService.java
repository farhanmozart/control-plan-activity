package com.mantap.dashboard.service;

import com.mantap.dashboard.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.entity.tasks.RefreshTokenEntity;
import com.mantap.dashboard.model.request.PostAuthChangePasswordRequest;
import com.mantap.dashboard.repository.RefreshTokenRepository;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.CHANGE_PASSWORD_SUCCESS_MESSAGE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;

@Service
@RequiredArgsConstructor
public class PostAuthChangePasswordService extends ResponseUtil {
    private final UsersRepository  usersRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponse<Void> execute(PostAuthChangePasswordRequest input) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ERROR_CODE_UNAUTHORIZED, ERROR_TITLE_UNAUTHORIZED, ERROR_MESSAGE_UNAUTHORIZED);
        }

        String nip = authentication.getName();

        UsersEntity usersEntity = usersRepository.findByNip(nip).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        validatePassword(usersEntity, input);
        usersEntity.setPassword(passwordEncoder.encode(input.getNewPassword()));
        long currentTokenVersion = usersEntity.getTokenVersion() == null ? 0L : usersEntity.getTokenVersion();
        usersEntity.setTokenVersion(currentTokenVersion + 1);

        revokeRefreshTokens(usersEntity.getUserId());
        SecurityContextHolder.clearContext();
        return ResponseUtil.success(SUCCESS_CODE, CHANGE_PASSWORD_SUCCESS_MESSAGE, null);
    }

    private void validatePassword(UsersEntity user, PostAuthChangePasswordRequest input) {
        if (!passwordEncoder.matches(input.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ERROR_CODE_10010, ERROR_TITLE_10010, ERROR_MESSAGE_10010);
        }

        if (!input.getNewPassword().equals(input.getConfirmPassword())) {
            throw new BusinessException(ERROR_CODE_10011, ERROR_TITLE_10011, ERROR_MESSAGE_10011);
        }

        if (passwordEncoder.matches(input.getNewPassword(), user.getPassword())) {
            throw new BusinessException(ERROR_CODE_10012, ERROR_TITLE_10012, ERROR_MESSAGE_10012);
        }
    }

    private void revokeRefreshTokens(String userId) {
        List<RefreshTokenEntity> tokens = refreshTokenRepository.findByUserIdAndRevokedAtIsNull(userId);
        LocalDateTime now = LocalDateTime.now();
        tokens.forEach(token -> token.setRevokedAt(now));
    }
}