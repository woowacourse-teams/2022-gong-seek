package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.exception.ArticleTitleNullOrEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleTitleTooLongException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class Title {

    private static final int MIN_TITLE_LENGTH = 1;
    private static final int MAX_TITLE_LENGTH = 500;

    @Column(name = "title", nullable = false)
    private String value;

    public Title(String value) {
        validateNullOrEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.trim().length() < MIN_TITLE_LENGTH) {
            throw new ArticleTitleNullOrEmptyException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_TITLE_LENGTH) {
            throw new ArticleTitleTooLongException(value.length());
        }
    }
}
