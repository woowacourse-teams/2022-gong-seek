package com.woowacourse.gongseek.member.domain;

import com.woowacourse.gongseek.member.exception.NameNullOrEmptyException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Name {

    private static final int MIN_NAME_LENGTH = 1;
    private String value;

    public Name(String value){
        validateNullOrEmpty(value);
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.trim().length() < MIN_NAME_LENGTH) {
            throw new NameNullOrEmptyException();
        }
    }

}
