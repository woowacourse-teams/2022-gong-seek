package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class CategoryTest {

    @Test
    void 없는_카테고리를_찾을_경우_예외를_발생한다() {
        String category = "juri";

        assertThatThrownBy(() -> Category.from(category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }

    @Test
    void 카테고리가_일치한다() {
        String category = "question";

        assertDoesNotThrow(() -> Category.from(category));
    }
}
