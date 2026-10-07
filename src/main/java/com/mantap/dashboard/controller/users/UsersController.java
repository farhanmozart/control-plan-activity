package com.mantap.dashboard.controller.users;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.AuthApproveUserRequest;
import com.mantap.dashboard.model.request.UserSignUpRequest;
import com.mantap.dashboard.model.response.AuthResponse.UserDto;
import com.mantap.dashboard.model.response.PendingUserDto;
import com.mantap.dashboard.service.GetPendingUsersService;
import com.mantap.dashboard.service.PostApproveUserService;
import com.mantap.dashboard.service.PostUserSignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final PostUserSignUpService postUsersSignUpService;
    private final PostApproveUserService postApproveUserService;
    private final GetPendingUsersService getPendingUsersService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<UserDto> login(@Valid @RequestBody UserSignUpRequest request) {
        return postUsersSignUpService.execute(request);
    }

    @PostMapping(value = "/approve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<UserDto> approveUser(@Valid @RequestBody AuthApproveUserRequest nip) {
        return postApproveUserService.execute(nip);
    }

    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<List<PendingUserDto>> getPendingUsers() {
        return getPendingUsersService.execute();
    }
}