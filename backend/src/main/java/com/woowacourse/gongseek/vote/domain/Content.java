package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.VoteItemNullOrEmptyException;
import com.woowacourse.gongseek.vote.exception.VoteItemTooLongException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 300;

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
    private String value;

    public Content(String value) {
        validateNullOrEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new VoteItemNullOrEmptyException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new VoteItemTooLongException(value.length());
        }
    }
}
