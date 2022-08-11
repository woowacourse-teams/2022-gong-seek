package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.exception.ArticleTitleNullOrEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleTitleTooLongException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class TitleTest {

    @Test
    void 타이틀을_생성한다() {
        String value = "a".repeat(500);
        Title title = new Title(value);

        assertThat(title.getValue()).isEqualTo(value);
    }

    @Test
    void 타이틀이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Title(null))
                .isExactlyInstanceOf(ArticleTitleNullOrEmptyException.class)
                .hasMessage("게시글 제목은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 타이틀이_빈_경우_예외를_발생한다(String value) {
        assertThatThrownBy(() -> new Title(value))
                .isExactlyInstanceOf(ArticleTitleNullOrEmptyException.class)
                .hasMessage("게시글 제목은 비어있을 수 없습니다.");
    }

    @Test
    void 타이틀이_500자를_초과한_경우_예외를_발생한다() {
        String title = "t".repeat(501);

        assertThatThrownBy(() -> new Title(title))
                .isExactlyInstanceOf(ArticleTitleTooLongException.class)
                .hasMessage("게시글 제목은 500자를 초과할 수 없습니다.");
    }
}
