package com.woowacourse.gongseek.comment.domain;

import com.woowacourse.gongseek.comment.exception.CommentNullOrEmptyException;
import com.woowacourse.gongseek.comment.exception.CommentTooLongException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 10_000;

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
    private String value;

    public Content(String value) {
        validateNullOrEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new CommentNullOrEmptyException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new CommentTooLongException(value.length());
        }
    }
}
