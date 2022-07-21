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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings("NonAsciiCharacters")
@Import({JpaAuditingConfig.class, QuerydslConfig.class})
@DataJpaTest
class ArticleRepositoryTest {

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
        String title = "질문합니다.";
        String content = "내용입나다....";

        Article article = new Article(title, content, Category.QUESTION, member);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }

    @Test
    void 게시물이_없으면_빈_값을_반환한다() {
        List<Article> articles = articleRepository.findAll("question", "views", PageRequest.of(0, 1));

        assertThat(articles).hasSize(0);
    }

    @ParameterizedTest
    @CsvSource({"5, 5", "6, 5"})
    void 게시물을_5개씩_조회한다(int savingCount, int offset) {
        for (int i = 0; i < savingCount; i++) {
            articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        }

        List<Article> articles = articleRepository.findAll("question", "views", PageRequest.of(0, offset));

        assertThat(articles).hasSize(offset);
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시물을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        articleRepository.save(new Article("title", "content", Category.DISCUSSION, member));

        List<Article> articles = articleRepository.findAll(category, "views", PageRequest.of(0, 5));

        assertThat(articles).hasSize(expectedSize);
    }

    @Test
    void 게시물을_조회순으로_조회한다() {
        Article firstArticle = articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        Article secondArticle = articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        Article thirdArticle = articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        firstArticle.addViews();
        firstArticle.addViews();
        secondArticle.addViews();

        List<Article> articles = articleRepository.findAll("question", "views", PageRequest.of(0, 3));

        assertThat(articles).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }

    @Test
    void 게시물을_최신순으로_조회한다() {
        Article thirdArticle = articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        Article secondArticle = articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        Article firstArticle = articleRepository.save(new Article("title", "content", Category.QUESTION, member));
        firstArticle.addViews();
        firstArticle.addViews();
        secondArticle.addViews();

        List<Article> articles = articleRepository.findAll("question", "latest", PageRequest.of(0, 3));

        assertThat(articles).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }
}
