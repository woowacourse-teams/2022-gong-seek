package com.woowacourse.gongseek.article.domain.articletag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ArticleTagsTest {

    private ArticleTags articleTags;
    private Member member;
    private Article article;

    @BeforeEach
    void setUp() {
        articleTags = new ArticleTags();
        member = new Member("rennon", "brorae", "avatar.com");
        article = new Article("title", "content", Category.QUESTION, member, false);
    }

    @Test
    void 게시글에_태그를_등록할_수_있다() {
        Tag tag1 = new Tag("SPRING");
        Tag tag2 = new Tag("BACKEND");

        articleTags.addTags(article, new Tags(List.of(tag1, tag2)));

        assertThat(articleTags.getArticleTags()).hasSize(2);
    }

    @Test
    void 게시글에_중복된_태그는_등록할_수_없다() {
        Tag tag1 = new Tag("SPRING");
        Tag tag2 = new Tag("spring");

        assertThatThrownBy(() -> articleTags.addTags(article, new Tags(List.of(tag1, tag2))))
                .isInstanceOf(DuplicateTagException.class)
                .hasMessage("해시태그 이름은 중복될 수 없습니다.");
    }
}
