package com.mantap.dashboard.util;

import com.mantap.dashboard.util.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.mantap.dashboard.util.constant.Constant.*;

@Component
public class SecurityContextHelper {

    public String getCurrentNip() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ERROR_CODE_80000, ERROR_TITLE_80000, ERROR_MESSAGE_80000);
        }
        return authentication.getName();
    }
}