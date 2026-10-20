package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.response.UsersProfileResponse;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import com.mantap.dashboard.util.SecurityContextHelper;
import com.mantap.dashboard.util.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class GetUsersProfileService extends ResponseUtil {
    private final UsersRepository usersRepository;
    private final SecurityContextHelper securityContextHelper;

    public BaseResponse<UsersProfileResponse> execute() {
        String nip = securityContextHelper.getCurrentNip();
        UsersProjection usersProjection = usersRepository.findUsersByNip(nip)
                .orElseThrow(() -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        UsersProfileResponse response = UsersProfileResponse.builder()
                .userId(usersProjection.getUserId())
                .nip(usersProjection.getNip())
                .name(usersProjection.getName())
                .email(usersProjection.getEmail())
                .role(usersProjection.getRole())
                .position(usersProjection.getPosition())
                .division(usersProjection.getDivision())
                .department(usersProjection.getDepartment())
                .status(usersProjection.getStatus())
                .isActive(Integer.valueOf(1).equals(usersProjection.getIsActive()))
                .lastLoginAt(usersProjection.getLastLoginAt())
                .build();
        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);
    }

}