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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostActivateUserService extends ResponseUtil {
    private final UsersRepository usersRepository;

    @Transactional
    public BaseResponse<UserDto> execute(AuthUserRequest input) {
        UsersEntity entity = usersRepository.findByNip(input.getNip())
                .orElseThrow(() -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        validateUser(entity);
        entity.setIsActive(true);
        entity.setStatus(UserStatus.ACTIVE);

        long currentTokenVersion = entity.getTokenVersion() == null ? 0L : entity.getTokenVersion();
        entity.setTokenVersion(currentTokenVersion + 1);
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

    private void validateUser(UsersEntity user) {
        if (Boolean.TRUE.equals(user.getIsActive()) && user.getStatus() == UserStatus.ACTIVE) {
            throw new BusinessException(ERROR_CODE_10015, ERROR_TITLE_10015, ERROR_MESSAGE_10015);
        }

        if (user.getStatus() == UserStatus.PENDING_APPROVAL) {
            throw new BusinessException(ERROR_CODE_10016, ERROR_TITLE_10016, ERROR_MESSAGE_10016);
        }

        if (user.getStatus() == UserStatus.REJECTED) {
            throw new BusinessException(ERROR_CODE_10017, ERROR_TITLE_10017, ERROR_MESSAGE_10017);
        }
    }
}