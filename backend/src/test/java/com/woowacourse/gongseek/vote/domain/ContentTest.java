package com.woowacourse.gongseek.vote.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.vote.exception.VoteItemNullOrEmptyException;
import com.woowacourse.gongseek.vote.exception.VoteItemTooLongException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ContentTest {

    @Test
    void 투표내용이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Content(null))
                .isExactlyInstanceOf(VoteItemNullOrEmptyException.class)
                .hasMessage("투표 항목 내용은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 투표내용이_비어있는_경우_예외를_발생한다(String content) {
        assertThatThrownBy(() -> new Content(content))
                .isExactlyInstanceOf(VoteItemNullOrEmptyException.class)
                .hasMessage("투표 항목 내용은 비어있을 수 없습니다.");
    }

    @Test
    void 투표내용이_500자를_초과하면_예외를_발생한다() {
        String content = "a".repeat(501);
        assertThatThrownBy(() -> new Content(content))
                .isExactlyInstanceOf(VoteItemTooLongException.class)
                .hasMessageContaining("투표 항목 내용은 500자를 초과할 수 없습니다.");
    }
}
