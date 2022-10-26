package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.like.domain.Like;
import com.woowacourse.gongseek.like.domain.repository.LikeRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
public class PagingArticleRepositoryTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String ANONYMOUS_NAME = "익명";
    private static final String ANONYMOUS_AVATAR_URL = "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png";

    private final Member member = new Member("slo", "hanull", "avatar.com");

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PagingArticleRepository pagingArticleRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private LikeRepository likeRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 게시글이_없으면_빈_값을_반환한다() {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0L,
                Category.QUESTION.getValue(), "",
                member.getId(), PageRequest.ofSize(5));

        assertThat(articles).isEmpty();
    }

    @Test
    void 게시글을_5개씩_조회한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, true));
        }
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(
                null, 0L, Category.QUESTION.getValue(), "views", member.getId(), PageRequest.ofSize(5));

        assertAll(
                () -> assertThat(articles.getContent()).hasSize(5),
                () -> assertThat(articles.getContent().get(0).getTitle()).isEqualTo(TITLE),
                () -> assertThat(articles.getContent().get(0).getTag()).isEmpty(),
                () -> assertThat(articles.getContent().get(0).getAuthor().getName()).isEqualTo(ANONYMOUS_NAME),
                () -> assertThat(articles.getContent().get(0).getAuthor().getAvatarUrl()).isEqualTo(
                        ANONYMOUS_AVATAR_URL),
                () -> assertThat(articles.getContent().get(0).getContent()).isEqualTo(CONTENT),
                () -> assertThat(articles.getContent().get(0).getCategory()).isEqualTo(Category.QUESTION.getValue()),
                () -> assertThat(articles.getContent().get(0).getViews()).isEqualTo(0L),
                () -> assertThat(articles.getContent().get(0).getCommentCount()).isEqualTo(0L),
                () -> assertThat(articles.getContent().get(0).getLikeCount()).isEqualTo(0L),
                () -> assertThat(articles.getContent().get(0).getIsLike()).isFalse(),
                () -> assertThat(articles.hasNext()).isFalse()
        );
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시글을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.DISCUSSION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0L, category, "views",
                member.getId(),
                PageRequest.ofSize(5));

        assertThat(articles.getContent()).hasSize(expectedSize);
    }

    @Test
    void 게시글을_조회순으로_조회한다() {
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        firstArticle.addViews();
        firstArticle.addViews();
        secondArticle.addViews();

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0L,
                Category.QUESTION.getValue(),
                "views",
                member.getId(), PageRequest.ofSize(10));

        assertAll(
                () -> assertThat(articles.getContent().get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(articles.getContent().get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(articles.getContent().get(2).getId()).isEqualTo(thirdArticle.getId())
        );
    }

    @Test
    void 게시글을_최신순으로_조회한다() {
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, null,
                Category.QUESTION.getValue(),
                "latest",
                member.getId(), PageRequest.ofSize(3));

        assertAll(
                () -> assertThat(articles.getContent().get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(articles.getContent().get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(articles.getContent().get(2).getId()).isEqualTo(thirdArticle.getId())
        );
    }

    @Transactional
    @Test
    void 게시글을_추천순으로_조회하고_다음_데이터가_존재하지_않는다() {
        Article firstArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article thirdArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));

        likeRepository.save(new Like(firstArticle, member));
        articleRepository.increaseLikeCount(firstArticle.getId());
        likeRepository.save(new Like(firstArticle, newMember));
        articleRepository.increaseLikeCount(firstArticle.getId());
        likeRepository.save(new Like(secondArticle, member));
        articleRepository.increaseLikeCount(secondArticle.getId());

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByLikes(null, null,
                Category.QUESTION.getValue(),
                member.getId(), PageRequest.ofSize(3));

        assertAll(
                () -> assertThat(articles.getContent().get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(articles.getContent().get(0).getIsLike()).isTrue(),
                () -> assertThat(articles.getContent().get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(articles.getContent().get(1).getIsLike()).isTrue(),
                () -> assertThat(articles.getContent().get(2).getId()).isEqualTo(thirdArticle.getId()),
                () -> assertThat(articles.getContent().get(2).getIsLike()).isFalse(),
                () -> assertThat(articles.hasNext()).isFalse()
        );
    }

    @Transactional
    @Test
    void 게시글을_추천순으로_조회하고_다음_데이터가_존재한다() {
        Article firstArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));

        likeRepository.save(new Like(firstArticle, member));
        articleRepository.increaseLikeCount(firstArticle.getId());
        likeRepository.save(new Like(firstArticle, newMember));
        articleRepository.increaseLikeCount(firstArticle.getId());
        likeRepository.save(new Like(secondArticle, member));
        articleRepository.increaseLikeCount(secondArticle.getId());

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByLikes(null, null,
                Category.QUESTION.getValue(),
                member.getId(), PageRequest.ofSize(2));

        assertAll(
                () -> assertThat(articles.getContent().get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(articles.getContent().get(0).getIsLike()).isTrue(),
                () -> assertThat(articles.getContent().get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(articles.getContent().get(1).getIsLike()).isTrue(),
                () -> assertThat(articles.hasNext()).isTrue()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"this is wooteco", "is", "this", "wooteco"})
    void 제목으로_게시글을_검색한다(String searchText) {
        articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am judy", "hello", Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(null, searchText, 0L,
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wow", "w"})
    void 띄어쓰기와_대소문자_관계_없이_내용으로_게시글을_검색한다(String searchText) {
        articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am 주디", "hello", Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(null, searchText, 0L,
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(1);
    }

    @Test
    void 게시글을_5개씩_검색한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(null, "title", 0L,
                PageRequest.ofSize(5));

        assertThat(articles.getContent()).hasSize(5);
    }

    @Test
    void 게시글이_없을_때_검색하면_빈_값을_반환한다() {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(null, "empty", 0L,
                PageRequest.ofSize(5));

        assertThat(articles.getContent()).isEmpty();
    }

    @Test
    void 유저_이름을_이용하여_게시글을_검색한다() {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByAuthor(null, member.getName(),
                member.getId(),
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"spring", "SPring", "SPRING", "java", "JAVA", "jaVA"})
    void 태그_이름_하나로_검색한다(String tag) {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        firstArticle.addTag(new Tags(tags));
        secondArticle.addTag(new Tags(tags));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByTag(null, member.getId(), List.of(tag),
                PageRequest.ofSize(2));

        assertAll(
                () -> assertThat(articles.getContent()).hasSize(2),
                () -> assertThat(articles.getContent().get(0).getTag()).hasSameElementsAs(secondArticle.getTagNames()),
                () -> assertThat(articles.getContent().get(1).getTag()).hasSameElementsAs(firstArticle.getTagNames()),
                () -> assertThat(articles.hasNext()).isFalse()
        );
    }

    @Test
    void tagsText가_비어있으면_빈_리스트가_나온다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        firstArticle.addTag(new Tags(tags));
        secondArticle.addTag(new Tags(tags));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByTag(null, member.getId(),
                new ArrayList<>(),
                PageRequest.ofSize(2));

        assertAll(
                () -> assertThat(articles.getContent()).hasSize(0),
                () -> assertThat(articles.hasNext()).isFalse()
        );
    }

    @Test
    void 태그_이름_여러개로_검색한다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));
        Article thirdArticle = articleRepository.save(
                new Article("title3", "content3", Category.DISCUSSION, member, false));
        Tag springTag = new Tag("spring");
        Tag javaTag = new Tag("java");
        List<Tag> firstTags = List.of(springTag, javaTag);
        List<Tag> secondTags = List.of(springTag);
        List<Tag> thirdTags = List.of(javaTag);
        tagRepository.saveAll(firstTags);
        firstArticle.addTag(new Tags(firstTags));
        secondArticle.addTag(new Tags(secondTags));
        thirdArticle.addTag(new Tags(thirdTags));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByTag(null, member.getId(),
                List.of("spring", "java"), PageRequest.ofSize(5));

        assertThat(articles.getContent()).hasSize(3);
        assertThat(articles.getContent().get(0).getTag()).hasSameElementsAs(thirdArticle.getTagNames());
        assertThat(articles.getContent().get(1).getTag()).hasSameElementsAs(secondArticle.getTagNames());
        assertThat(articles.getContent().get(2).getTag()).hasSameElementsAs(firstArticle.getTagNames());
    }
}
