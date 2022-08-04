package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class ViewsTest {

    @Test
    void 조회수를_생성한다() {
        Views views = new Views();

        assertThat(views.getValue()).isEqualTo(0);
    }

    @Test
    void 조회수를_증가시킬_수_있다() {
        Views views = new Views();

        views.addValue();

        assertThat(views.getValue()).isEqualTo(1);
    }
}
