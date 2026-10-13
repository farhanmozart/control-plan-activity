package com.mantap.dashboard.controller.authentication;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.AuthLoginRequest;
import com.mantap.dashboard.model.request.AuthRefreshTokenRequest;
import com.mantap.dashboard.model.response.AuthRefreshTokenResponse;
import com.mantap.dashboard.model.response.AuthResponse;
import com.mantap.dashboard.service.PostAuthLoginService;
import com.mantap.dashboard.service.PostAuthLogoutService;
import com.mantap.dashboard.service.PostAuthRefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final PostAuthLoginService postAuthLoginService;
    private final PostAuthLogoutService postAuthLogoutService;
    private final PostAuthRefreshTokenService postAuthRefreshTokenService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return postAuthLoginService.execute(request);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public BaseResponse<Void> logout() {
        return postAuthLogoutService.execute();
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AuthRefreshTokenResponse> refreshToken(@Valid @RequestBody AuthRefreshTokenRequest request) {
        return postAuthRefreshTokenService.execute(request);
    }

}