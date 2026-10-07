package com.mantap.dashboard.controller.users;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.UserSignUpRequest;
import com.mantap.dashboard.model.response.AuthLoginResponse.UserDto;
import com.mantap.dashboard.service.PostUserSignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final PostUserSignUpService postUsersSignUpService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<UserDto> login(@Valid @RequestBody UserSignUpRequest request) {
        return postUsersSignUpService.execute(request);
    }
}