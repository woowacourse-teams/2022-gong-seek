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

    private static final int MAX_CONTENT_LENGTH = 1000;

    @Lob
    @Column(name = "content")
    private String value;

    public Content(String value) {
        validateContentLength(value);
        this.value = value;
    }

    private void validateContentLength(String value) {
        if (Objects.isNull(value) || value.isBlank() || value.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("내용의 길이는 1~1000이여야 합니다.");
        }
    }
}
