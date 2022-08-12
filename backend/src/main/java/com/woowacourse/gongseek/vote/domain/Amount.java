package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.InvalidVoteAmountException;
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
public class Amount {

    @Column(name = "amount", nullable = false)
    private int value = 0;

    public Amount(int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(int value) {
        if (value < 0) {
            throw new InvalidVoteAmountException(value);
        }
    }

    public Amount increase() {
        return new Amount(value + 1);
    }

    public Amount decrease() {
        return new Amount(value - 1);
    }
}
