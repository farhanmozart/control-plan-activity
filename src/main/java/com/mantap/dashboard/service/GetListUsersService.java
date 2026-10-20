package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.response.UsersProfileResponse;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class GetListUsersService extends ResponseUtil {
    private final UsersRepository usersRepository;

    public BaseResponse<List<UsersProfileResponse>> execute(){
        List<UsersProjection> usersResponse = usersRepository.findAllUsers();
        List<UsersProfileResponse> response =
                usersResponse.stream()
                        .map(user -> UsersProfileResponse.builder()
                                .userId(user.getUserId())
                                .nip(user.getNip())
                                .name(user.getName())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .position(user.getPosition())
                                .division(user.getDivision())
                                .department(user.getDepartment())
                                .status(user.getStatus())
                                .isActive(Integer.valueOf(1).equals(user.getIsActive()))
                                .lastLoginAt(user.getLastLoginAt())
                                .build())
                        .toList();
        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);
    }
}