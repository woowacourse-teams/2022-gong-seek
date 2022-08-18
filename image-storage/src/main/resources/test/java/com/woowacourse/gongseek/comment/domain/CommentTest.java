package com.woowacourse.gongseek.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.member.domain.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CommentTest {

    private static final Member member = new Member("slo", "hanull", "avatar.com");
    private static final Article article = new Article("title", "content", Category.QUESTION, member, false);

    @Test
    void 댓글을_생성한다() {
        Comment comment = new Comment("댓글1", member, article, false);

        assertAll(
                () -> assertThat(comment.getContent()).isEqualTo("댓글1"),
                () -> assertThat(comment.getMember()).isEqualTo(member),
                () -> assertThat(comment.getArticle()).isEqualTo(article),
                () -> assertThat(comment.isAnonymous()).isFalse()
        );
    }

    @Test
    void 기명인경우_댓글의_작성자인지_확인한다() {
        Comment comment = new Comment("댓글1", member, article, false);

        assertThat(comment.isAuthor(member)).isTrue();
    }

    @Test
    void 익명인경우_댓글의_작성자인지_확인한다() {
        Comment comment = new Comment("댓글1", member, article, false);

        assertThat(comment.isAuthor(member)).isTrue();
    }
}
