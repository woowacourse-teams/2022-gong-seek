package com.gongseek.imagestorage.exception;

import com.gongseek.imagestorage.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageFileIOException.class)
    public ResponseEntity<ErrorResponse> imageException(ImageFileIOException e) {
        log.info(String.format("ImageException: %s", e));
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandleException(Exception e) {
        log.error(String.format("unHandleException: %s", e));
        return ResponseEntity.internalServerError().body(new ErrorResponse("알 수 없는 서버 에러 입니다."));
    }
}
