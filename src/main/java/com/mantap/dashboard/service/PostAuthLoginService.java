package com.mantap.dashboard.service;

import com.mantap.dashboard.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.request.AuthLoginRequest;
import com.mantap.dashboard.model.response.AuthLoginResponse;
import com.mantap.dashboard.model.response.AuthLoginResponse.UserDto;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.JwtUtil;
import com.mantap.dashboard.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.LOGIN_SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.LOGIN_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostAuthLoginService extends ResponseUtil {
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public BaseResponse<AuthLoginResponse> execute(AuthLoginRequest input) {
        UsersProjection userResponse = usersRepository.findUsersByNip(input.getNip()).orElseThrow(() ->
                new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        validateUser(userResponse, input);

        String token = jwtUtil.generateToken(userResponse);

        UserDto userDto = UserDto.builder()
                .userId(userResponse.getUserId())
                .nip(userResponse.getNip())
                .name(userResponse.getName())
                .division(userResponse.getDivision())
                .department(userResponse.getDepartment())
                .role(userResponse.getRole())
                .build();

        AuthLoginResponse loginResponse = AuthLoginResponse.builder()
                .authToken(token)
                .user(userDto)
                .build();

        return ResponseUtil.success(LOGIN_SUCCESS_CODE, LOGIN_SUCCESS_MESSAGE, loginResponse);
    }

    private void validateUser(UsersProjection user, AuthLoginRequest input) {
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new BusinessException(ERROR_CODE_10001, ERROR_TITLE_10001, ERROR_MESSAGE_10001);
        }

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new BusinessException(ERROR_CODE_10002, ERROR_TITLE_10002, ERROR_MESSAGE_10002);
        }
    }
}