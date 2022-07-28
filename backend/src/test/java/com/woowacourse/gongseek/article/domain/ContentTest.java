package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.exception.ArticleContentNullOrEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleContentTooLongException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class ContentTest {

    @Test
    void 내용을_생성한다() {
        String value = "a".repeat(10000);
        Content content = new Content(value);

        assertThat(content.getValue()).isEqualTo(value);
    }

    @Test
    void 내용이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Content(null))
                .isInstanceOf(ArticleContentNullOrEmptyException.class)
                .hasMessage("게시글 내용은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 내용이_비어있는_경우_예외를_발생한다(String content) {
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(ArticleContentNullOrEmptyException.class)
                .hasMessage("게시글 내용은 비어있을 수 없습니다.");
    }

    @Test
    void 내용이_10000자를_초과하면_예외를_발생한다() {
        String content = "a".repeat(10001);
        assertThatThrownBy(() -> new Content(content))
                .isInstanceOf(ArticleContentTooLongException.class)
                .hasMessage("게시글 내용은 10000자를 초과할 수 없습니다.");
    }
}
