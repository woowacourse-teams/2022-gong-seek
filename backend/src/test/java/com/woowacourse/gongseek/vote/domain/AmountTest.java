package com.woowacourse.gongseek.vote.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.vote.exception.InvalidVoteAmountException;
import org.junit.jupiter.api.Test;

class AmountTest {

    @Test
    void 투표수가_음수일경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Amount(-1))
                .isExactlyInstanceOf(InvalidVoteAmountException.class)
                .hasMessageContaining("투표수는 양수여야만 합니다.");
    }

    @Test
    void 투표수의_초기값은_0이다() {
        Amount amount = new Amount();

        assertThat(amount.getValue()).isEqualTo(0);
    }

    @Test
    void 투표수가_1씩_증가한다() {
        Amount beforeAmount = new Amount();
        Amount afterAmount = beforeAmount.increase();

        assertThat(afterAmount.getValue()).isEqualTo(beforeAmount.getValue() + 1);
    }

    @Test
    void 투표수가_1씩_감소한다() {
        Amount beforeAmount = new Amount(2);
        Amount afterAmount = beforeAmount.decrease();

        assertThat(afterAmount.getValue()).isEqualTo(beforeAmount.getValue() - 1);
    }
}
