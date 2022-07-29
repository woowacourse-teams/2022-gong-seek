package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.config.QuerydslConfig;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("NonAsciiCharacters")
@Import({JpaAuditingConfig.class, QuerydslConfig.class})
@DataJpaTest
class ArticleRepositoryTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private final Member member = new Member("slo", "hanull", "avatar.com");

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 질문을_저장한다() {
        Article article = new Article(TITLE, CONTENT, Category.QUESTION, member);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }

    @Test
    void 게시물이_없으면_빈_값을_반환한다() {
        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "", 5);

        assertThat(articles).hasSize(0);
    }

    @Test
    void 게시물을_5개씩_조회한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        }
        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views", 5);

        assertThat(articles).hasSize(5);
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시물을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        articleRepository.save(new Article(TITLE, CONTENT, Category.DISCUSSION, member));

        List<Article> articles = articleRepository.findAllByPage(null, 0, category, "views", 5);

        assertThat(articles).hasSize(expectedSize);
    }

    @Test
    void 게시물을_조회순으로_조회한다() {
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        firstArticle.addViews();
        firstArticle.addViews();
        secondArticle.addViews();

        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views", 10);

        assertThat(articles).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }

    @Test
    void 게시물을_최신순으로_조회한다() {
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));

        List<Article> articles = articleRepository.findAllByPage(null, null, Category.QUESTION.getValue(), "latest", 3);

        assertThat(articles).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 9999, 10_000})
    void 질문_내용의_길이가_10000자까지_가능하다(int count) {
        String title = "질문합니다.";
        String content = "a".repeat(count);

        Article article = new Article(title, content, Category.QUESTION, member);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }

    @ParameterizedTest
    @ValueSource(strings = {"this is wooteco", "is", "THIS IS WOOTECO", "IS", "THiS Is WOOteCO", "Is", "thisis",
            "thisIs", "this iswooteco"})
    void 띄어쓰기와_대소문자_관계_없이_제목으로_게시물을_검색한다(String searchText) {
        Article article = articleRepository.save(new Article("this is wooteco", "wow", Category.QUESTION, member));
        articleRepository.save(new Article("i am judy", "hello", Category.QUESTION, member));

        List<Article> articles = articleRepository.searchByTitleOrContentLike(null, searchText, 2);

        assertThat(articles).containsExactly(article);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wow", "w", "WOW", "W", "WoW", "W ow", "w o w"})
    void 내용과_일치하는_게시물을_검색한다(String searchText) {
        Article article = articleRepository.save(new Article("this is wooteco", "wow", Category.QUESTION, member));
        articleRepository.save(new Article("i am 주디", "hello", Category.QUESTION, member));

        List<Article> articles = articleRepository.searchByTitleOrContentLike(null, searchText, 2);

        assertThat(articles).containsExactly(article);
    }

    @Test
    void 게시물을_5개씩_검색한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member));
        }
        List<Article> articles = articleRepository.searchByTitleOrContentLike(null, "title", 5);

        assertThat(articles).hasSize(5);
    }

    @Test
    void 게시물이_없을_때_검색하면_빈_값을_반환한다() {
        List<Article> articles = articleRepository.searchByTitleOrContentLike(null, "empty", 5);

        assertThat(articles).hasSize(0);
    }
}
