package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.exception.ArticleContentNullException;
import org.junit.jupiter.api.Test;

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
                .isExactlyInstanceOf(ArticleContentNullException.class)
                .hasMessage("게시글 내용은 null일 수 없습니다.");
    }
}
