package com.woowacourse.gongseek.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public ApplicationException(HttpStatus httpStatus) {
        ExceptionType exceptionType = ExceptionType.from(this.getClass());
        this.httpStatus = httpStatus;
        this.errorCode = exceptionType.getErrorCode();
        this.message = exceptionType.getMessage();
    }

    public ApplicationException(HttpStatus httpStatus, String optionalMessage) {
        ExceptionType exceptionType = ExceptionType.from(this.getClass());
        this.httpStatus = httpStatus;
        this.errorCode = exceptionType.getErrorCode();
        this.message = exceptionType.getMessage() + optionalMessage;
    }
}
