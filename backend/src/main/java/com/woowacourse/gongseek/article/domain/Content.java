package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.exception.ArticleContentNullOrEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleContentTooLongException;
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

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
    private String value;

    public Content(String value) {
        validateNullOrEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.trim().isBlank()) {
            throw new ArticleContentNullOrEmptyException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new ArticleContentTooLongException();
        }
    }
}
