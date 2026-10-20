package com.mantap.dashboard.controller.users;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.request.AuthUserRequest;
import com.mantap.dashboard.model.request.PostUpdateUserProfileRequest;
import com.mantap.dashboard.model.request.UserSignUpRequest;
import com.mantap.dashboard.model.response.AuthResponse.UserDto;
import com.mantap.dashboard.model.response.PendingUserDto;
import com.mantap.dashboard.model.response.UsersDetailResponse;
import com.mantap.dashboard.model.response.UsersProfileResponse;
import com.mantap.dashboard.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mantap.dashboard.util.constant.Constant.LOGGED_ADMIN;
import static com.mantap.dashboard.util.constant.Constant.LOGGED_USER;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final PostUserSignUpService postUsersSignUpService;
    private final PostApproveUserService postApproveUserService;
    private final PostRejectUserService postRejectUserService;
    private final GetPendingUsersService getPendingUsersService;
    private final GetUsersProfileService getUsersProfileService;
    private final PostUpdateUserProfileService postUpdateUserProfileService;
    private final GetListUsersService getListUsersService;
    private final GetUsersDetailService getUsersDetailService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_ADMIN)
    public BaseResponse<List<UsersProfileResponse>> getListUsers() {
        return getListUsersService.execute();
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<UserDto> login(@Valid @RequestBody UserSignUpRequest request) {
        return postUsersSignUpService.execute(request);
    }

    @PostMapping(value = "/approve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_ADMIN)
    public BaseResponse<UserDto> approveUser(@Valid @RequestBody AuthUserRequest nip) {
        return postApproveUserService.execute(nip);
    }

    @PostMapping(value = "/reject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<UserDto> rejectUser(@Valid @RequestBody AuthUserRequest nip) {
        return postRejectUserService.execute(nip);
    }

    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_ADMIN)
    public BaseResponse<List<PendingUserDto>> getPendingUsers() {
        return getPendingUsersService.execute();
    }
    
    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_USER)
    public BaseResponse<UsersProfileResponse> getProfile() {
        return getUsersProfileService.execute();
    }

    @PatchMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_USER)
    public BaseResponse<UsersProfileResponse> updateProfile(@Valid @RequestBody PostUpdateUserProfileRequest request) {
        return postUpdateUserProfileService.execute(request);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(LOGGED_ADMIN)
    public BaseResponse<UsersDetailResponse> getUserDetail(@PathVariable String userId) {
        return getUsersDetailService.execute(userId);
    }
}