package com.woowacourse.gongseek.image.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class UnsupportedFilExtensionException extends BadRequestException {

    public UnsupportedFilExtensionException(final String extension) {
        super(String.format("[%s]", extension));
    }
}
