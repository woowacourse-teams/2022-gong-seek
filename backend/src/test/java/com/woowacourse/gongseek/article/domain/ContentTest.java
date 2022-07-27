package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.contentOf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class ContentTest {

    @Test
    void 내용의_길이는_1부터_10000이여야_한다() {
        String value = "a".repeat(10000);
        var content = new Content(value);

        assertThat(content).isInstanceOf(Content.class);
    }

    @Test
    void 내용이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Content(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용의 길이는 1~10000이여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 내용이_비어있는_경우_예외를_발생한다(String content) {
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용의 길이는 1~10000이여야 합니다.");
    }

    @Test
    void 내용이_10000자를_초과하면_예외를_발생한다() {
        String content = "a".repeat(10001);
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용의 길이는 1~10000이여야 합니다.");
    }
}
