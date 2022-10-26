package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
public class ArticleTagRepositoryTest {

    private final Member member = new Member("slo", "hanull", "avatar.com");

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 마이페이지에서_내가_작성한_게시글을_조회할_때_댓글_갯수도_함께_조회한다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.DISCUSSION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        commentRepository.save(new Comment("content1", member, firstArticle, false));
        commentRepository.save(new Comment("content2", member, firstArticle, false));
        commentRepository.save(new Comment("content3", member, firstArticle, false));

        commentRepository.save(new Comment("content1", member, secondArticle, false));
        commentRepository.save(new Comment("content2", member, secondArticle, false));

        List<Article> myPageArticles = articleRepository.findAllByMemberId(member.getId());

        assertAll(
                () -> assertThat(myPageArticles).hasSize(2),
                () -> assertThat(myPageArticles.get(0)).isEqualTo(firstArticle)
        );
    }

    @Test
    void 특정_해시태그로_저장되어_있는_게시글이_있는지_확인한다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        List<Long> tagIds = tagRepository.saveAll(tags).stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        article.addTag(new Tags(tags));

        boolean firstResult = articleTagRepository.existsArticleByTagId(tagIds.get(0));
        boolean secondResult = articleTagRepository.existsArticleByTagId(tagIds.get(1));
        boolean thirdResult = articleTagRepository.existsArticleByTagId(999L);

        assertAll(
                () -> assertThat(firstResult).isTrue(),
                () -> assertThat(secondResult).isTrue(),
                () -> assertThat(thirdResult).isFalse()
        );
    }

    @Test
    void 존재하지_않는_게시글_식별자로_단건_조회_시_Optional을_반환한다() {
        Optional<Article> article = articleRepository.findByIdWithAll(500L);

        assertThat(article).isEmpty();
    }
}
