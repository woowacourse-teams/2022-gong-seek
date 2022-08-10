package com.woowacourse.gongseek.common.exception;

import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorCode = ExceptionType.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getErrorCode();
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorCode, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> runtimeException() {
        String message = ExceptionType.UNHANDLED_EXCEPTION.getMessage();
        String errorCode = ExceptionType.UNHANDLED_EXCEPTION.getErrorCode();
        return ResponseEntity.internalServerError().body(new ErrorResponse(errorCode, message));
    }
}
