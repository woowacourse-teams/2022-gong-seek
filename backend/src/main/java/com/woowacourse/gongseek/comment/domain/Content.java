package com.woowacourse.gongseek.comment.domain;

import com.woowacourse.gongseek.comment.exception.CommentNullOrEmptyException;
import com.woowacourse.gongseek.comment.exception.CommentTooLongException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 10_000;

    @Lob
    @Column(name = "content")
    private String value;

    public Content(String value) {
        validateContentLength(value);
        this.value = value;
    }

    private void validateContentLength(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new CommentNullOrEmptyException();
        }
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new CommentTooLongException();
        }
    }
}
