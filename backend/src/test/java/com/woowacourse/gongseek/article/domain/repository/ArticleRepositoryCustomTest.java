package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.like.domain.Like;
import com.woowacourse.gongseek.like.domain.repository.LikeRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SuppressWarnings("NonAsciiCharacters")
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@RepositoryTest
public class ArticleRepositoryCustomTest {

    private final Member member = new Member("slo", "hanull", "avatar.com");

    private final ArticleRepository articleRepository;
    private final ArticleRepositoryCustom articleRepositoryCustom;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final TestEntityManager testEntityManager;

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

        List<MyPageArticleDto> myPageArticles = articleRepositoryCustom.findAllByMemberIdWithCommentCount(
                member.getId());

        assertAll(
                () -> assertThat(myPageArticles).hasSize(2),
                () -> assertThat(myPageArticles.get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(
                                new MyPageArticleDto(firstArticle.getId(), firstArticle.getTitle(),
                                        firstArticle.getCategory(),
                                        3L, 0, LocalDateTime.now(), LocalDateTime.now()))
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

        boolean firstResult = articleRepositoryCustom.existsArticleByTagId(tagIds.get(0));
        boolean secondResult = articleRepositoryCustom.existsArticleByTagId(tagIds.get(1));
        boolean thirdResult = articleRepositoryCustom.existsArticleByTagId(999L);

        assertAll(
                () -> assertThat(firstResult).isTrue(),
                () -> assertThat(secondResult).isTrue(),
                () -> assertThat(thirdResult).isFalse()
        );
    }

    @Test
    void 게시글_단건_조회_시_작성자_투표생성여부_좋아요여부_좋아요갯수를_반환한다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.DISCUSSION, member, false));
        Tag firstTag = new Tag("Spring");
        Tag secondTag = new Tag("Rennon");
        tagRepository.saveAll(List.of(firstTag, secondTag));
        article.addTag(new Tags(List.of(firstTag, secondTag)));
        likeRepository.save(new Like(article, member));

        ArticleDto foundArticleDto = articleRepositoryCustom.findByIdWithAll(article.getId(), member.getId()).get();

        assertThat(foundArticleDto)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(
                        new ArticleDto(
                                article.getTitle(),
                                List.of("SPRING", "RENNON"),
                                member.getName(),
                                member.getAvatarUrl(),
                                article.getContent(),
                                true,
                                article.getViews(),
                                false,
                                true,
                                article.isAnonymous(),
                                1L,
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        )
                );
    }

    @Test
    void 존재하지_않는_게시글_식별자로_단건_조회_시_Optional을_반환한다() {
        Optional<ArticleDto> articleDto = articleRepositoryCustom.findByIdWithAll(500L, member.getId());

        assertThat(articleDto).isEmpty();
    }

    @Test
    void 게시글의_아이디로_태그를_찾을_수_있다() {
        Article article = articleRepository.save(
                new Article("title", "content", Category.DISCUSSION, member, false));
        Tag tag = tagRepository.save(new Tag("spring"));
        article.addTag(new Tags(List.of(tag)));

        testEntityManager.flush();
        testEntityManager.clear();

        List<String> tags = articleRepositoryCustom.findTagNamesByArticleId(article.getId());

        assertThat(tags).isEqualTo(article.getTagNames());
    }
}
