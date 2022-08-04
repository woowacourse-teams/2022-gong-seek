package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.member.domain.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ArticleTest {

    private static final Member member = new Member("slo", "hanull", "avatar.com");

    @Test
    void 게시물을_생성한다() {
        Article article = new Article("title", "content", Category.QUESTION, member);

        assertAll(
                () -> assertThat(article.getTitle()).isEqualTo("title"),
                () -> assertThat(article.getContent()).isEqualTo("content"),
                () -> assertThat(article.getCategory()).isEqualTo(Category.QUESTION)
        );
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

        LocalDateTime now = LocalDateTime.now();
        article.update(updatedTitle, updatedContent, now);

        assertAll(
                () -> assertThat(article.getTitle()).isEqualTo(updatedTitle),
                () -> assertThat(article.getContent()).isEqualTo(updatedContent),
                () -> assertThat(article.getUpdatedAt()).isEqualTo(now)
        );
    }
}
