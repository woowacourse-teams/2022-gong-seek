package com.woowacourse.gongseek.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String optionalMessage) {
        super(HttpStatus.NOT_FOUND, optionalMessage);
    }
}
