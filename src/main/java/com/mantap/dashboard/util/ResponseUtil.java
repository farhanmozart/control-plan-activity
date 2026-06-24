package com.mantap.dashboard.util;

import com.mantap.dashboard.model.dto.BaseResponse;

import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SUCCESS_MESSAGE;

public class ResponseUtil {

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> success(String code, String message, T data) {
        return BaseResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}