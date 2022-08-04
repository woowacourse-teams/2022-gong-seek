package com.woowacourse.gongseek.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ExceptionType exceptionType = ExceptionType.from(this.getClass());
    private final String errorCode;
    private final String message;

    public ApplicationException() {
        this.errorCode = exceptionType.getErrorCode();
        this.message = exceptionType.getMessage();
    }
}
