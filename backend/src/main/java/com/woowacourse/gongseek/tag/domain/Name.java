package com.woowacourse.gongseek.tag.domain;

import com.woowacourse.gongseek.tag.exception.TagNameLengthException;
import com.woowacourse.gongseek.tag.exception.TagNameNullOrBlankException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Name {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 20;

    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    private String value;

    public Name(String value) {
        validateNullOrBlank(value);
        validateLength(value);
        this.value = value.trim().toUpperCase();
    }

    private void validateNullOrBlank(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new TagNameNullOrBlankException();
        }
    }

    private void validateLength(String name) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new TagNameLengthException();
        }
    }
}
