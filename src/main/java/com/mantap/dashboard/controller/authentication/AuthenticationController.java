package com.mantap.dashboard.controller.authentication;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.AuthLoginRequest;
import com.mantap.dashboard.model.response.AuthLoginResponse;
import com.mantap.dashboard.service.PostAuthLoginService;
import com.mantap.dashboard.service.PostAuthLogoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return postAuthLoginService.execute(request);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Void> logout() {
        return postAuthLogoutService.execute();
    }
}