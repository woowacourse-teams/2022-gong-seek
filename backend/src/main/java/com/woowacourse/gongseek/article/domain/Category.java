package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.exception.ArticleCategoryNotFoundException;
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
                .orElseThrow(ArticleCategoryNotFoundException::new);
    }
}
