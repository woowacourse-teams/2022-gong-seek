package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class ContentTest {

    @Test
    void 댓글이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Content(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용의 길이는 1~10000이여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 댓글이_비어있는_경우_예외를_발생한다(String content) {
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용의 길이는 1~10000이여야 합니다.");
    }

    @Test
    void 댓글이_10000자를_초과하면_예외를_발생한다() {
        String content = "a".repeat(10001);
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용의 길이는 1~10000이여야 합니다.");
    }
}
