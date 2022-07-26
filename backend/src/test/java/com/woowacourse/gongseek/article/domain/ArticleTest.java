package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.exception.ArticleContentLengthException;
import com.woowacourse.gongseek.article.exception.ArticleTitleEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleTitleTooLongException;
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
                .isInstanceOf(ArticleTitleEmptyException.class)
                .hasMessage("게시글 제목은 비어있을 수 없습니다.");
    }

    @Test
    void 타이틀이_500자를_초과한_경우_예외를_발생한다() {
        String title = "t".repeat(501);
        String content = "content";
        Category question = Category.QUESTION;

        assertThatThrownBy(() -> new Article(title, content, question, member))
                .isInstanceOf(ArticleTitleTooLongException.class)
                .hasMessage("게시글 제목은 500자를 초과할 수 없습니다.");
    }

    @Test
    void 컨텐트가_1000자를_초과한_경우_예외를_발생한다() {
        String title = "title";
        String content = "c".repeat(1001);
        Category question = Category.QUESTION;

        assertThatThrownBy(() -> new Article(title, content, question, member))
                .isInstanceOf(ArticleContentLengthException.class)
                .hasMessage("게시글 내용은 10000자를 초과할 수 없습니다.");
    }

    @Test
    void 게시물의_제목과_내용을_수정한다() {
        Member member = new Member("기론", "gyuchool", "www.asdcxz.com");
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
