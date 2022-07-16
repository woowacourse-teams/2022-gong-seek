package com.woowacourse.gongseek.comment.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 10000;

    private String content;

    public Content(String content) {
        validateContentLength(content);
        this.content = content;
    }

    private void validateContentLength(String content) {
        if (Objects.isNull(content) || content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("댓글의 길이는 1~10000이여야 합니다.");
        }
    }
}
