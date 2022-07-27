package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
class ArticleTest {

    private static final Member member = new Member("slo", "hanull", "avatar.com");

    @Test
    void 게시물을_생성한다() {
        var article = new Article("title", "contetn", Category.QUESTION, member);

        assertThat(article).isInstanceOf(Article.class);
    }

    @Test
    void 게시글의_작성자인지_확인한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member);

        assertThat(article.isAuthor(member)).isTrue();
    }

    @Test
    void 게시물의_제목과_내용을_수정한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member);
        String updatedTitle = "updatedTitle";
        String updatedContent = "수정된 내용입니다~~";

        article.update(updatedTitle, updatedContent);

        assertAll(
                () -> assertThat(article.getTitle()).isEqualTo(updatedTitle),
                () -> assertThat(article.getContent()).isEqualTo(updatedContent)
        );
    }
}
