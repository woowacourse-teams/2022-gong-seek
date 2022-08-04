package com.woowacourse.gongseek.article.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {

    QUESTION("question"),
    DISCUSSION("discussion");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static Category from(String value) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }
}
