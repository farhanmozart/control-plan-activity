package com.mantap.dashboard;

public class BusinessException extends RuntimeException {
    private final String errorCode;
    private final String errorTitle;
    private final String errorMessage;

    public BusinessException(String errorCode, String errorTitle, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
    }
}