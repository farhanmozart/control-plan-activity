package com.mantap.dashboard;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final String title;

    public BusinessException(String code, String title, String message) {
        super(message);
        this.code = code;
        this.title = title;
    }
}