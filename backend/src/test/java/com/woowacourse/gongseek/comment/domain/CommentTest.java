package com.woowacourse.gongseek.comment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CommentTest {

    private final Member member = new Member("jurl", "jurlring", "url");
    private final Article article = new Article("title", "content", Category.DISCUSSION, member);

    @Test
    void 댓글이_null_이면_예외를_발생한다() {
        assertThatThrownBy(() -> new Comment(null, member, article))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글의 길이는 1~10000이여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 댓글이_비어있는_경우_예외를_발생한다(String content) {
        assertThatThrownBy(() -> new Comment(content, member, article))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글의 길이는 1~10000이여야 합니다.");
    }

    @Test
    void 댓글이_10000자를_초과하면_예외를_발생한다() {
        String content = "a".repeat(10001);
        assertThatThrownBy(() -> new Comment(content, member, article))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("댓글의 길이는 1~10000이여야 합니다.");
    }
}
