package com.mantap.dashboard.service;

import com.mantap.dashboard.util.exception.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.dto.RefreshTokenResult;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.request.AuthLoginRequest;
import com.mantap.dashboard.model.response.AuthResponse;
import com.mantap.dashboard.model.response.AuthResponse.UserDto;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.JwtUtil;
import com.mantap.dashboard.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.LOGIN_SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.LOGIN_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostAuthLoginService extends ResponseUtil {
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public BaseResponse<AuthResponse> execute(AuthLoginRequest input) {

        String nip = input.getNip().trim();
        UsersEntity usersResponse = usersRepository.findByNip(nip).orElseThrow(() ->
                new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));
        validateUser(usersResponse, input);

        String token = jwtUtil.generateToken(usersResponse);
        RefreshTokenResult refreshToken = refreshTokenService.create(usersResponse);
        usersResponse.setLastLoginAt(LocalDateTime.now());

        UserDto userDto = UserDto.builder()
                .userId(usersResponse.getUserId())
                .nip(usersResponse.getNip())
                .name(usersResponse.getName())
                .role(usersResponse.getRole())
                .build();

        AuthResponse loginResponse = AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.rawToken())
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpiration()/1000)
                .user(userDto)
                .build();

        return ResponseUtil.success(LOGIN_SUCCESS_CODE, LOGIN_SUCCESS_MESSAGE, loginResponse);
    }

    private void validateUser(UsersEntity user, AuthLoginRequest input) {
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new BusinessException(ERROR_CODE_10001, ERROR_TITLE_10001, ERROR_MESSAGE_10001);
        }

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new BusinessException(ERROR_CODE_10002, ERROR_TITLE_10002, ERROR_MESSAGE_10002);
        }
    }
}