package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.request.AuthUserRequest;
import com.mantap.dashboard.model.response.AuthResponse.UserDto;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import com.mantap.dashboard.util.enumeration.UserStatus;
import com.mantap.dashboard.util.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostRejectUserService extends ResponseUtil {
    private final UsersRepository usersRepository;

    public BaseResponse<UserDto> execute(AuthUserRequest input) {
        UsersEntity entity = usersRepository.findByNip(input.getNip()).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));
        validateUser(entity);

        entity.setStatus(UserStatus.REJECTED);
        entity.setIsActive(false);
        usersRepository.saveAndFlush(entity);

        UsersProjection projection = usersRepository.findUsersByNip(entity.getNip())
                .orElseThrow(() -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        UserDto response = UserDto.builder()
                .userId(projection.getUserId())
                .nip(projection.getNip())
                .name(projection.getName())
                .role(projection.getRole())
                .division(projection.getDivision())
                .department(projection.getDepartment())
                .status(projection.getStatus())
                .build();

        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);

    }

    private void validateUser(UsersEntity entity) {
        if (entity.getStatus() != UserStatus.PENDING_APPROVAL) {
            throw new BusinessException(ERROR_CODE_10014, ERROR_TITLE_10014, ERROR_MESSAGE_10014);
        }
    }
}