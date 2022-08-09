package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import java.util.List;
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

    @Test
    void 게시글의_해시태그를_추가한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member, false);
        Tag tag1 = new Tag("spring");
        Tag tag2 = new Tag("backend");

        article.addTags(new Tags(List.of(tag1, tag2)));

        assertThat(article.getArticleTags()).hasSize(2);
    }

    @Test
    void 게시글에_중복되는_해시태그를_추가하면_예외가_발생한다() {
        Article article = new Article("제목", "내용", Category.QUESTION, member, false);
        Tag tag1 = new Tag("spring");
        Tag tag2 = new Tag("SPRING");

        assertThatThrownBy(() -> article.addTags(new Tags(List.of(tag1, tag2))))
                .isInstanceOf(DuplicateTagException.class)
                .hasMessage("해시태그 이름은 중복될 수 없습니다.");
    }
}
