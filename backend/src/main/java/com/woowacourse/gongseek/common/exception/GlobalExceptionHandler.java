package com.woowacourse.gongseek.common.exception;

import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(ApplicationException e) {
        log.info(String.format("Application Exception : %s", e));
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(String.format("MethodArgumentNotValidException : %s", e));
        String errorCode = ExceptionType.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getErrorCode();
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorCode, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> runtimeException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error(String.format("UnHandled Exception : %s\n" + "%s:%s:%s", e, stackTrace[0].getClassName(),
                stackTrace[0].getMethodName(), stackTrace[0].getLineNumber()));
        String message = ExceptionType.UNHANDLED_EXCEPTION.getMessage();
        String errorCode = ExceptionType.UNHANDLED_EXCEPTION.getErrorCode();
        return ResponseEntity.internalServerError().body(new ErrorResponse(errorCode, message));
    }
}
