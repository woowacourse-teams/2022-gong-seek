package com.woowacourse.gongseek.image.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class UnsupportedImageExtension extends BadRequestException {

    public UnsupportedImageExtension(final String extension) {
        super(extension);
    }
}
