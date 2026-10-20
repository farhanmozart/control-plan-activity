package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.projection.UserDetailProjection;
import com.mantap.dashboard.model.response.UsersDetailResponse;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import com.mantap.dashboard.util.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class GetUsersDetailService extends ResponseUtil {
    private final UsersRepository usersRepository;

    public BaseResponse<UsersDetailResponse> execute(String userId) {

        UserDetailProjection user = usersRepository.findUserDetailByUserId(userId)
                .orElseThrow(() -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        UsersDetailResponse response = UsersDetailResponse.builder()
                .userId(user.getUserId())
                .nip(user.getNip())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .position(user.getPosition())
                .divisionId(user.getDivisionId())
                .division(user.getDivision())
                .departmentId(user.getDepartmentId())
                .department(user.getDepartment())
                .isActive(Integer.valueOf(1).equals(user.getIsActive()))
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);
    }
}