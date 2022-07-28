package com.woowacourse.gongseek.article.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class Content {

    private static final int MAX_CONTENT_LENGTH = 10_000;

    @Lob
    @Column(name = "content", nullable = false)
    private String value;

    public Content(String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (Objects.isNull(value) || value.isBlank() || value.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("내용의 길이는 1~10000이여야 합니다.");
        }
    }
}
