package com.woowacourse.gongseek.vote.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.vote.exception.InvalidVoteExpiryAtException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class VoteTest {

    @Test
    void 만료일을_시작일_전으로_설정한_경우_예외를_발생한다() {
        assertThatThrownBy(() -> new Vote(LocalDateTime.now().minusDays(1)))
                .isInstanceOf(InvalidVoteExpiryAtException.class)
                .hasMessage("투표 만료일 설정이 잘못되었습니다.");
    }

    @Test
    void 설정_가능한_최대_만료일을_초과한_경우_예외를_발생한다() {
        assertThatThrownBy(() -> new Vote(LocalDateTime.now().minusDays(1)))
                .isInstanceOf(InvalidVoteExpiryAtException.class)
                .hasMessage("투표 만료일 설정이 잘못되었습니다.");
    }
}
