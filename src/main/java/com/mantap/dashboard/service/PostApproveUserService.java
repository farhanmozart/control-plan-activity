package com.mantap.dashboard.service;

import com.mantap.dashboard.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.request.AuthApproveUserRequest;
import com.mantap.dashboard.model.response.AuthResponse.UserDto;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.APPROVAL_SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.APPROVAL_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostApproveUserService extends ResponseUtil {
    private final UsersRepository usersRepository;

    @Transactional
    public BaseResponse<UserDto> execute(AuthApproveUserRequest input) {
        UsersEntity entity = usersRepository.findByNip(input.getNip()).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));
        entity.setIsActive(true);
        usersRepository.saveAndFlush(entity);

        UsersProjection projection = usersRepository.findUsersByNip(input.getNip()).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        UserDto usersResponse = UserDto.builder()
                .userId(projection.getUserId())
                .nip(projection.getNip())
                .name(projection.getName())
                .role(projection.getRole())
                .division(projection.getDivision())
                .department(projection.getDepartment())
                .status("APPROVED")
                .build();

        return ResponseUtil.success(APPROVAL_SUCCESS_CODE, APPROVAL_SUCCESS_MESSAGE, usersResponse);
    }
}