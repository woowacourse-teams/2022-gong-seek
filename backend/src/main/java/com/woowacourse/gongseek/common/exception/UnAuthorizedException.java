package com.woowacourse.gongseek.common.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ApplicationException {

    public UnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
