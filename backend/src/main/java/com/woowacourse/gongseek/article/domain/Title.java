package com.woowacourse.gongseek.article.domain;

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

    private static final int MIN_TITLE_LENGTH = 0;
    private static final int MAX_TITLE_LENGTH = 500;

    @Column(name = "title", nullable = false)
    private String value;

    public Title(String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (Objects.isNull(value) || value.trim().length() <= MIN_TITLE_LENGTH || value.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("타이틀의 길이는 0 이상 500 이하여야합니다.");
        }
    }
}
