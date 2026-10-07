package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mantap.dashboard.util.constant.ResponseConstant.LOGOUT_SUCCESS_CODE;

@Service
@RequiredArgsConstructor
public class PostAuthLogoutService {
    public BaseResponse<Void> execute() {
        return ResponseUtil.success(
                LOGOUT_SUCCESS_CODE,
                LOGOUT_SUCCESS_CODE,
                null);
    }
}
