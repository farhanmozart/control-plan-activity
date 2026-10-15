package com.mantap.dashboard.controller.authentication;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.*;
import com.mantap.dashboard.model.response.AuthRefreshTokenResponse;
import com.mantap.dashboard.model.response.AuthResponse;
import com.mantap.dashboard.model.response.GetAuthSessionResponse;
import com.mantap.dashboard.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.mantap.dashboard.util.constant.Constant.LOGGED_USER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final PostAuthLoginService postAuthLoginService;
    private final PostAuthLogoutService postAuthLogoutService;
    private final PostAuthRefreshTokenService postAuthRefreshTokenService;
    private final PostAuthChangePasswordService postAuthChangePasswordService;
    private final PostAuthForgotPasswordService postAuthForgotPasswordService;
    private final PostAuthResetPasswordService postAuthResetPasswordService;
    private final GetAuthSessionService getAuthSessionService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return postAuthLoginService.execute(request);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_USER)
    public BaseResponse<Void> logout() {
        return postAuthLogoutService.execute();
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AuthRefreshTokenResponse> refreshToken(@Valid @RequestBody AuthRefreshTokenRequest request) {
        return postAuthRefreshTokenService.execute(request);
    }

    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_USER)
    public BaseResponse<Void> changePassword(@Valid @RequestBody PostAuthChangePasswordRequest request) {
        return postAuthChangePasswordService.execute(request);
    }

    @PostMapping(value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> forgotPassword(@Valid @RequestBody PostAuthForgotPasswordRequest request) {
        return postAuthForgotPasswordService.execute(request);
    }

    @PostMapping(value = "/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> resetPassword(@Valid @RequestBody PostAuthResetPasswordRequest request) {
        return postAuthResetPasswordService.execute(request);
    }

    @GetMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_USER)
    public BaseResponse<GetAuthSessionResponse> getSession() {
        return getAuthSessionService.execute();
    }
}