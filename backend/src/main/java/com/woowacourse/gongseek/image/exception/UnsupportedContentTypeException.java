package com.woowacourse.gongseek.image.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class UnsupportedContentTypeException extends BadRequestException {

    public UnsupportedContentTypeException() {
    }

    public UnsupportedContentTypeException(final String extension) {
        super(String.format("[%s]", extension));
    }
}
