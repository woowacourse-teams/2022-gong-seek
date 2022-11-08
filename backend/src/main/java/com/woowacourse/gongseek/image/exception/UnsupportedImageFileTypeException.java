package com.woowacourse.gongseek.image.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class UnsupportedImageFileTypeException extends BadRequestException {

    public UnsupportedImageFileTypeException() {
    }

    public UnsupportedImageFileTypeException(final String extension) {
        super(String.format("[%s]", extension));
    }
}
