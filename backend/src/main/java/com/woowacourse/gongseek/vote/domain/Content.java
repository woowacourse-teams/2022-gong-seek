package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.VoteItemNullOrEmptyException;
import com.woowacourse.gongseek.vote.exception.VoteItemTooLongException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 100;

    private String value;

    public Content(String value) {
        validateNullOrEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.trim().isBlank()) {
            throw new VoteItemNullOrEmptyException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new VoteItemTooLongException();
        }
    }
}
