package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
class ArticleTest {

    private static final Member member = new Member("slo", "hanull", "avatar.com");

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 타이틀이_빈_경우_예외를_발생한다(String title) {
        String content = "content";
        Category question = Category.QUESTION;

        assertThatThrownBy(() -> new Article(title, content, question, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }

    @Test
    void 타이틀이_500자를_초과한_경우_예외를_발생한다() {
        String title = "t".repeat(501);
        String content = "content";
        Category question = Category.QUESTION;

        assertThatThrownBy(() -> new Article(title, content, question, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }

    @Test
    void 컨텐트가_1000자를_초과한_경우_예외를_발생한다() {
        String title = "title";
        String content = "c".repeat(1001);
        Category question = Category.QUESTION;

        assertThatThrownBy(() -> new Article(title, content, question, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("컨텐트의 길이는 1000 이하여야합니다.");
    }
}
