package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.request.PostUpdateUserProfileRequest;
import com.mantap.dashboard.model.response.UsersProfileResponse;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import com.mantap.dashboard.util.SecurityContextHelper;
import com.mantap.dashboard.util.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostUpdateUserProfileService extends ResponseUtil {
    private final UsersRepository usersRepository;
    private final SecurityContextHelper securityContextHelper;

    @Transactional
    public BaseResponse<UsersProfileResponse> execute(PostUpdateUserProfileRequest input) {
        String nip = securityContextHelper.getCurrentNip();
        UsersEntity usersEntity = usersRepository.findByNip(nip)
                .orElseThrow(() -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        validateEmail(usersEntity, input);
        validateProfile(usersEntity, input);

        UsersProjection projection = usersRepository.findUsersByNip(nip)
                .orElseThrow(() -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        UsersProfileResponse response = UsersProfileResponse.builder()
                .userId(projection.getUserId())
                .nip(projection.getNip())
                .name(projection.getName())
                .email(projection.getEmail())
                .role(projection.getRole())
                .position(projection.getPosition())
                .division(projection.getDivision())
                .department(projection.getDepartment())
                .status(projection.getStatus())
                .lastLoginAt(projection.getLastLoginAt())
                .build();

        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);
    }

    private void validateEmail(UsersEntity usersEntity, PostUpdateUserProfileRequest input) {
        if (input.getEmail() == null) {
            return;
        }

        String email = input.getEmail().trim().toLowerCase();

        if (email.equalsIgnoreCase(usersEntity.getEmail())) {
            return;
        }

        if (usersRepository.existsByEmail(email)) {
            throw new BusinessException(ERROR_CODE_10005, ERROR_TITLE_10005, ERROR_MESSAGE_10005);
        }
    }

    private void validateProfile(UsersEntity usersEntity, PostUpdateUserProfileRequest input) {
        if (input.getName() != null) {
            usersEntity.setName(input.getName().trim());
        }

        if (input.getEmail() != null) {
            usersEntity.setEmail(input.getEmail().trim().toLowerCase());
        }

        if (input.getPosition() != null) {
            usersEntity.setPosition(input.getPosition().trim());
        }
    }
}