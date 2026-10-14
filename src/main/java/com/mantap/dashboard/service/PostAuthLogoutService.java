package com.mantap.dashboard.service;

import com.mantap.dashboard.util.exception.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.LOGOUT_SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.LOGOUT_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostAuthLogoutService extends ResponseUtil {

    private final UsersRepository usersRepository;

    @Transactional
    public BaseResponse<Void> execute() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ERROR_CODE_80000, ERROR_TITLE_80000, ERROR_MESSAGE_80000);
        }

        String nip = authentication.getName();
        UsersEntity user = usersRepository.findByNip(nip).orElseThrow(() -> new BusinessException(ERROR_CODE_80000,
                ERROR_TITLE_80000, ERROR_MESSAGE_80000));

        long currentTokenVersion = user.getTokenVersion() == null ? 0L : user.getTokenVersion();

        user.setTokenVersion(currentTokenVersion + 1);
        usersRepository.save(user);
        SecurityContextHolder.clearContext();
        return ResponseUtil.success(LOGOUT_SUCCESS_CODE, LOGOUT_SUCCESS_MESSAGE, null);
    }
}
