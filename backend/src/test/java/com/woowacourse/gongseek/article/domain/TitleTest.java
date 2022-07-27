package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class TitleTest {

    @Test
    void 타이틀의_길이는_0부터_500이여야_한다() {
        String value = "a".repeat(500);
        var title = new Title(value);

        assertThat(title).isInstanceOf(Title.class);
    }

    @Test
    void 타이틀이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Title(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 타이틀이_빈_경우_예외를_발생한다(String value) {
        assertThatThrownBy(() -> new Title(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }

    @Test
    void 타이틀이_500자를_초과한_경우_예외를_발생한다() {
        String title = "t".repeat(501);

        assertThatThrownBy(() -> new Title(title))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }
}
