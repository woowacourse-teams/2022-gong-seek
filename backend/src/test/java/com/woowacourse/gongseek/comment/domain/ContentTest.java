package com.woowacourse.gongseek.comment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.comment.exception.CommentNullOrEmptyException;
import com.woowacourse.gongseek.comment.exception.CommentTooLongException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ContentTest {

    @Test
    void 댓글이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Content(null))
                .isExactlyInstanceOf(CommentNullOrEmptyException.class)
                .hasMessage("댓글은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 댓글이_비어있는_경우_예외를_발생한다(String content) {
        assertThatThrownBy(() -> new Content(content))
                .isExactlyInstanceOf(CommentNullOrEmptyException.class)
                .hasMessage("댓글은 비어있을 수 없습니다.");
    }

    @Test
    void 댓글이_10000자를_초과하면_예외를_발생한다() {
        String content = "a".repeat(10001);
        assertThatThrownBy(() -> new Content(content))
                .isExactlyInstanceOf(CommentTooLongException.class)
                .hasMessageContaining("댓글은 10000자를 초과할 수 없습니다.");
    }
}
