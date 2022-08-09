package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.member.domain.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ArticleTest {

    private static final Member member = new Member("slo", "hanull", "avatar.com");

    @Test
    void 게시글을_생성한다() {
        Article article = new Article("title", "content", Category.QUESTION, member, false);

        assertAll(
                () -> assertThat(article.getTitle()).isEqualTo("title"),
                () -> assertThat(article.getContent()).isEqualTo("content"),
                () -> assertThat(article.getCategory()).isEqualTo(Category.QUESTION),
                () -> assertThat(article.isAnonymous()).isFalse()
        );
    }

    @Test
    void 기명인경우_게시글의_작성자인지_확인한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member, false);

        assertThat(article.isAuthor(member)).isTrue();
    }

    @Test
    void 익명인경우_게시글의_작성자인지_확인한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member, true);

        assertThat(article.isAuthor(member)).isTrue();
    }

    @Test
    void 익명_게시글일때_작성자인지_확인한다() {
        String cipherId = "cipherId";
        Member member = new Member("익명", cipherId, "anonymousAvatarUrl");
        Article article = new Article("제목", "내용", Category.QUESTION, member, true);

        assertThat(article.isAnonymousAuthor(cipherId)).isTrue();
    }

    @Test
    void 게시글의_제목과_내용을_수정한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member, false);
        String updatedTitle = "updatedTitle";
        String updatedContent = "수정된 내용입니다~~";

        article.update(updatedTitle, updatedContent);

        assertAll(
                () -> assertThat(article.getTitle()).isEqualTo(updatedTitle),
                () -> assertThat(article.getContent()).isEqualTo(updatedContent)
        );
    }
}
