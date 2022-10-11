package com.woowacourse.gongseek.image.domain;

import com.woowacourse.gongseek.image.exception.FileNameEmptyException;
import java.util.Objects;
import lombok.Getter;

@Getter
public class FileName {

    private final String value;

    public FileName(final String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new FileNameEmptyException();
        }
        this.value = value;
    }
}
