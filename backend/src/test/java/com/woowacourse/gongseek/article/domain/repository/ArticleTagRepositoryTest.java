package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.config.QuerydslConfig;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("NonAsciiCharacters")
@Import({JpaAuditingConfig.class, QuerydslConfig.class})
@DataJpaTest
class ArticleTagRepositoryTest {

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 게시글_별_해시태그를_저장한다() {
        Member member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        Tag tag1 = tagRepository.save(new Tag("Spring"));
        Tag tag2 = tagRepository.save(new Tag("Java"));
        articleTagRepository.save(new ArticleTag(article, tag1));
        articleTagRepository.save(new ArticleTag(article, tag2));

        List<ArticleTag> articleTags = articleTagRepository.findAllByArticleId(article.getId());

        assertThat(articleTags).hasSize(2);
    }

    @Test
    void 게시글_별_해시태그를_삭제한다() {
        Member member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        Tag tag1 = tagRepository.save(new Tag("Spring"));
        Tag tag2 = tagRepository.save(new Tag("Java"));
        articleTagRepository.save(new ArticleTag(article, tag1));
        articleTagRepository.save(new ArticleTag(article, tag2));

        articleTagRepository.deleteAllByArticleId(article.getId());
        List<ArticleTag> articleTags = articleTagRepository.findAllByArticleId(article.getId());

        assertThat(articleTags).hasSize(0);
    }
}
