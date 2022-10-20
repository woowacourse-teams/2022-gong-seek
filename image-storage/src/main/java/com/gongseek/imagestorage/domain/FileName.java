package com.gongseek.imagestorage.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class FileName {

    private final String value;

    public FileName(final String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
}
