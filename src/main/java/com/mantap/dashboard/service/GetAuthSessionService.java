package com.mantap.dashboard.service;

import com.mantap.dashboard.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.response.GetAuthSessionResponse;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class GetAuthSessionService extends ResponseUtil {
    private final UsersRepository userRepository;

    public BaseResponse<GetAuthSessionResponse> execute() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ERROR_CODE_80000, ERROR_TITLE_80000, ERROR_MESSAGE_80000);
        }

        String nip = authentication.getName();
        UsersProjection userResponse = userRepository.findUsersByNip(nip).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10000, ERROR_TITLE_10000, ERROR_MESSAGE_10000));

        validateUser(userResponse);

        GetAuthSessionResponse response = GetAuthSessionResponse.builder()
                .authenticated(true)
                .userId(userResponse.getUserId())
                .nip(userResponse.getNip())
                .name(userResponse.getName())
                .role(userResponse.getRole())
                .division(userResponse.getDivision())
                .department(userResponse.getDepartment())
                .build();

        return ResponseUtil.success(SUCCESS_CODE, SUCCESS_MESSAGE, response);
    }

    private void validateUser(UsersProjection user) {
        if (!Integer.valueOf(1).equals(user.getIsActive())) {
            throw new BusinessException(ERROR_CODE_80000, ERROR_TITLE_80000, ERROR_MESSAGE_80000);
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new BusinessException(ERROR_CODE_80000, ERROR_TITLE_80000, ERROR_MESSAGE_80000);
        }
    }
}