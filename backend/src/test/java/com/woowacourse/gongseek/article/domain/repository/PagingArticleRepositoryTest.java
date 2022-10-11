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
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SuppressWarnings("NonAsciiCharacters")
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@RepositoryTest
public class PagingArticleRepositoryTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private final Member member = new Member("slo", "hanull", "avatar.com");

    private final ArticleRepository articleRepository;
    private final PagingArticleRepository pagingArticleRepository;
    private final ArticleRepositoryCustom articleRepositoryCustom;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 게시글이_없으면_빈_값을_반환한다() {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0,
                Category.QUESTION.getValue(), "",
                member.getId(), PageRequest.ofSize(5));

        assertThat(articles).isEmpty();
    }

    @Test
    void 게시글을_5개씩_조회한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0,
                Category.QUESTION.getValue(),
                "views",
                member.getId(), PageRequest.ofSize(5));

        assertThat(articles.getContent()).hasSize(5);
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시글을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.DISCUSSION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0, category, "views",
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

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(null, 0,
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

    @ParameterizedTest
    @ValueSource(strings = {"this is wooteco", "is", "this", "wooteco"})
    void 제목으로_게시글을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am judy", "hello", Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(null, searchText, 0L,
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wow", "w"})
    void 띄어쓰기와_대소문자_관계_없이_내용으로_게시글을_검색한다(String searchText) {
        Article article = articleRepository.save(
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
        likeRepository.save(new Like(firstArticle, newMember));
        likeRepository.save(new Like(secondArticle, member));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByLikes(null, null,
                Category.QUESTION.getValue(),
                member.getId(), PageRequest.ofSize(3));

        assertAll(
                () -> assertThat(articles.getContent().get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(articles.getContent().get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(articles.getContent().get(2).getId()).isEqualTo(thirdArticle.getId()),
                () -> assertThat(articles.hasNext()).isFalse()
        );
    }

    @Test
    void 게시글을_추천순으로_조회하고_다음_데이터가_존재한다() {
        Article firstArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));

        likeRepository.save(new Like(firstArticle, member));
        likeRepository.save(new Like(firstArticle, newMember));
        likeRepository.save(new Like(secondArticle, member));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByLikes(null, null,
                Category.QUESTION.getValue(),
                member.getId(), PageRequest.ofSize(2));

        assertAll(
                () -> assertThat(articles.getContent().get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(articles.getContent().get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(articles.hasNext()).isTrue()
        );
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

        assertThat(articles.getContent()).hasSize(2);
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
                List.of("spring", "java"), PageRequest.ofSize(3));

        assertThat(articles.getContent()).hasSize(3);
    }

    @Test
    void 조회한_게시글의_태그들을_검색한다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        firstArticle.addTag(new Tags(tags));
        secondArticle.addTag(new Tags(tags));

        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByTag(null, member.getId(),
                List.of("spring"),
                PageRequest.ofSize(2));

        List<Long> articleIds = articles.getContent().stream()
                .map(ArticlePreviewDto::getId)
                .collect(Collectors.toList());

        Map<Long, List<String>> foundTags = articleRepositoryCustom.findTags(articleIds);
        assertAll(
                () -> assertThat(foundTags.get(firstArticle.getId()).containsAll(tags)),
                () -> assertThat(foundTags.get(secondArticle.getId()).containsAll(tags))
        );
    }
}