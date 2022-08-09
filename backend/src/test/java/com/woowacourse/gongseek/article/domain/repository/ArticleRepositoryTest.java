package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        Article article = new Article(TITLE, CONTENT, Category.QUESTION, member, false);
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
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views", 5);

        assertThat(articles).hasSize(5);
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시물을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.DISCUSSION, member, false));

        List<Article> articles = articleRepository.findAllByPage(null, 0, category, "views", 5);

        assertThat(articles).hasSize(expectedSize);
    }

    @Test
    void 게시물을_조회순으로_조회한다() {
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        firstArticle.addViews();
        firstArticle.addViews();
        secondArticle.addViews();

        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views", 10);

        assertThat(articles).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }

    @Test
    void 게시물을_최신순으로_조회한다() {
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        List<Article> articles = articleRepository.findAllByPage(null, null, Category.QUESTION.getValue(), "latest", 3);

        assertThat(articles).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 9999, 10_000})
    void 질문_내용의_길이가_10000자까지_가능하다(int count) {
        String title = "질문합니다.";
        String content = "a".repeat(count);

        Article article = new Article(title, content, Category.QUESTION, member, false);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }

    @ParameterizedTest
    @ValueSource(strings = {"this is wooteco", "is", "THIS IS WOOTECO", "IS", "THiS Is WOOteCO", "Is", "thisis",
            "thisIs", "this iswooteco"})
    void 띄어쓰기와_대소문자_관계_없이_제목으로_게시물을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am judy", "hello", Category.QUESTION, member, false));

        List<Article> articles = articleRepository.searchByContainingText(null, 2, searchText);

        assertThat(articles).containsExactly(article);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wow", "w", "WOW", "W", "WoW", "W ow", "w o w"})
    void 띄어쓰기와_대소문자_관계_없이_내용으로_게시물을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am 주디", "hello", Category.QUESTION, member, false));

        List<Article> articles = articleRepository.searchByContainingText(null, 2, searchText);

        assertThat(articles).containsExactly(article);
    }

    @Test
    void 게시물을_5개씩_검색한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        List<Article> articles = articleRepository.searchByContainingText(null, 5, "title");

        assertThat(articles).hasSize(5);
    }

    @Test
    void 게시물이_없을_때_검색하면_빈_값을_반환한다() {
        List<Article> articles = articleRepository.searchByContainingText(null, 5, "empty");

        assertThat(articles).hasSize(0);
    }

    @Test
    void 회원이_작성한_게시글들을_조회할_수_있다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        List<Article> articles = articleRepository.findAllByMemberId(member.getId());

        assertThat(articles).containsExactly(firstArticle, secondArticle);
    }

    @Test
    void 회원이_작성한_게시글들을_수정할_수_있다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));

        article.update("수정 제목", "내용 바꿉니다.");
        articleRepository.flush();

        assertAll(
                () -> assertThat(article.getTitle()).isEqualTo("수정 제목"),
                () -> assertThat(article.getContent()).isEqualTo("내용 바꿉니다.")
        );
    }
}
