package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class ViewsTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 100})
    void 조회수는_0_이상이어야_한다(int value) {
        var views = new Views(value);

        assertThat(views).isInstanceOf(Views.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-999, -2, -1})
    void 조회수는_음수일_수_없다(int value) {
        assertThatThrownBy(() -> new Views(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("조회수는 음수일 수 없습니다.");
    }

    @Test
    void 조회수를_증가시킬_수_있다() {
        Views views = new Views(0);

        views.addValue();

        assertThat(views.getValue()).isEqualTo(1);
    }
}
