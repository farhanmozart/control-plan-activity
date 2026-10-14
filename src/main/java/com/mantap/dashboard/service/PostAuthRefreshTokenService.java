package com.mantap.dashboard.service;

import com.mantap.dashboard.util.exception.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.dto.RefreshTokenResult;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.entity.RefreshTokenEntity;
import com.mantap.dashboard.model.request.AuthRefreshTokenRequest;
import com.mantap.dashboard.model.response.AuthRefreshTokenResponse;
import com.mantap.dashboard.repository.RefreshTokenRepository;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.JwtUtil;
import com.mantap.dashboard.util.RefreshTokenUtil;
import com.mantap.dashboard.util.ResponseUtil;
import com.mantap.dashboard.util.enumeration.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostAuthRefreshTokenService extends ResponseUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenUtil refreshTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BaseResponse<AuthRefreshTokenResponse> execute(AuthRefreshTokenRequest input) {
        String tokenHash = refreshTokenUtil.hashToken(input.getRefreshToken());
        RefreshTokenEntity storedToken = refreshTokenRepository.findByTokenHash(tokenHash).orElseThrow(this::invalidRefreshToken);
        validateRefreshToken(storedToken);
        UsersEntity user = usersRepository.findById(storedToken.getUserId()).orElseThrow(this::invalidRefreshToken);
        validateUser(user, storedToken);
        storedToken.setRevokedAt(LocalDateTime.now());

        RefreshTokenResult newRefreshToken = refreshTokenService.create(user);
        storedToken.setReplacedByTokenId(newRefreshToken.entity().getRefreshTokenId());
        String newAccessToken = jwtUtil.generateToken(user);

        AuthRefreshTokenResponse response = AuthRefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.rawToken())
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpiration() / 1000)
                .build();

        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);
    }

    private void validateRefreshToken(RefreshTokenEntity token) {
        if (token.getRevokedAt() != null) {
            throw invalidRefreshToken();
        }
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw invalidRefreshToken();
        }
    }

    private void validateUser(UsersEntity user, RefreshTokenEntity token) {
        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw invalidRefreshToken();
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw invalidRefreshToken();
        }

        long userTokenVersion = Optional.ofNullable(user.getTokenVersion()).orElse(0L);

        if (userTokenVersion != token.getTokenVersion()) {
            throw invalidRefreshToken();
        }
    }

    private BusinessException invalidRefreshToken() {
        return new BusinessException(ERROR_CODE_10009, ERROR_TITLE_10009, ERROR_MESSAGE_10009);
    }
}