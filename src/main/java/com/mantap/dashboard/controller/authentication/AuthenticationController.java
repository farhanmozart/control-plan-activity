package com.mantap.dashboard.controller.authentication;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.AuthLoginRequest;
import com.mantap.dashboard.model.response.AuthResponse;
import com.mantap.dashboard.service.PostAuthLoginService;
import com.mantap.dashboard.service.PostAuthLogoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final PostAuthLoginService postAuthLoginService;
    private final PostAuthLogoutService postAuthLogoutService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return postAuthLoginService.execute(request);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public BaseResponse<Void> logout() {
        return postAuthLogoutService.execute();
    }
}