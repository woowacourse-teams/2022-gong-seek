package com.woowacourse.gongseek.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private final String errorCode;
    private final String message;
}
