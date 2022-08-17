package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.config.QuerydslConfig;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 게시글을_저장한다() {
        Article article = new Article(TITLE, CONTENT, Category.QUESTION, member, false);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }

    @Test
    void 게시글이_없으면_빈_값을_반환한다() {
        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "", 5);

        assertThat(articles).isEmpty();
    }

    @Test
    void 게시글을_5개씩_조회한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        List<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views", 5);

        assertThat(articles).hasSize(5);
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시글을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.DISCUSSION, member, false));

        List<Article> articles = articleRepository.findAllByPage(null, 0, category, "views", 5);

        assertThat(articles).hasSize(expectedSize);
    }

    @Test
    void 게시글을_조회순으로_조회한다() {
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
    void 게시글을_최신순으로_조회한다() {
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
    void 띄어쓰기와_대소문자_관계_없이_제목으로_게시글을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am judy", "hello", Category.QUESTION, member, false));

        List<Article> articles = articleRepository.searchByContainingText(null, 2, searchText);

        assertThat(articles).containsExactly(article);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wow", "w", "WOW", "W", "WoW", "W ow", "w o w"})
    void 띄어쓰기와_대소문자_관계_없이_내용으로_게시글을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am 주디", "hello", Category.QUESTION, member, false));

        List<Article> articles = articleRepository.searchByContainingText(null, 2, searchText);

        assertThat(articles).containsExactly(article);
    }

    @Test
    void 게시글을_5개씩_검색한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        List<Article> articles = articleRepository.searchByContainingText(null, 5, "title");

        assertThat(articles).hasSize(5);
    }

    @Test
    void 게시글이_없을_때_검색하면_빈_값을_반환한다() {
        List<Article> articles = articleRepository.searchByContainingText(null, 5, "empty");

        assertThat(articles).isEmpty();
    }

    @Test
    void 유저_이름을_이용하여_게시글을_검색한다() {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        List<Article> articles = articleRepository.searchByAuthor(null, 2, member.getName());

        assertThat(articles).hasSize(2);
    }

    @Test
    void 회원들이_작성한_게시글들을_조회할_수_있다() {
        Member otherMember = new Member("rennon", "brorae", "avatar.com");
        memberRepository.save(otherMember);
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, otherMember, false));

        List<Long> memberIds = List.of(member.getId(), otherMember.getId());
        List<Article> articles = articleRepository.findAllByMemberIdIn(memberIds);

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

    @Test
    void 회원이_작성한_게시글들을_삭제할_수_있다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));

        articleRepository.deleteById(article.getId());

        assertThat(articleRepository.findAll()).isEmpty();
    }

    @Test
    void 회원은_게시글에_해시태그를_추가할_수_있다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        article.addTag(new Tags(tags));

        testEntityManager.flush();
        testEntityManager.clear();

        Article foundArticle = articleRepository.findById(article.getId()).get();

        assertAll(
                () -> assertThat(tagRepository.findAll()).hasSize(2),
                () -> assertThat(foundArticle.getArticleTags().getValue()).hasSize(2)
        );
    }

    @Test
    void 회원은_게시글에_해시태그를_수정할_수_있다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        article.addTag(new Tags(tags));

        testEntityManager.flush();
        testEntityManager.clear();

        Article firstFoundArticle = articleRepository.findById(article.getId()).get();
        Tag updatedTag = new Tag("backend");
        tagRepository.save(updatedTag);
        firstFoundArticle.updateTag(new Tags(List.of(updatedTag)));

        testEntityManager.flush();
        testEntityManager.clear();

        Article secondFoundArticle = articleRepository.findById(article.getId()).get();

        assertAll(
                () -> assertThat(tagRepository.findAll()).hasSize(3),
                () -> assertThat(secondFoundArticle.getArticleTags().getValue()).hasSize(1)
        );
    }

    @Test
    void 특정_해시태그로_저장되어_있는_게시글이_있는지_확인한다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        article.addTag(new Tags(tags));

        boolean firstResult = articleRepository.existsByTagName("SPRING");
        boolean secondResult = articleRepository.existsByTagName("JAVA");
        boolean thirdResult = articleRepository.existsByTagName("REACT");

        assertAll(
                () -> assertThat(firstResult).isTrue(),
                () -> assertThat(secondResult).isTrue(),
                () -> assertThat(thirdResult).isFalse()
        );
    }

    @Test
    void 태그_이름으로_게시글들을_조회한다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));
        List<Tag> firstTags = List.of(new Tag("spring"), new Tag("java"));
        List<Tag> secondTags = List.of(new Tag("react"), new Tag("html"));
        tagRepository.saveAll(firstTags);
        tagRepository.saveAll(secondTags);
        firstArticle.addTag(new Tags(firstTags));
        secondArticle.addTag(new Tags(secondTags));

        List<Article> articles = articleRepository.findAllByTagNameIn(
                List.of("SPRING", "JAVA", "REACT", "HTML"));

        assertThat(articles).hasSize(2);
    }

    @Test
    void 태그_이름으로_같은_태그의_게시글들을_조회한다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        tagRepository.saveAll(tags);
        firstArticle.addTag(new Tags(tags));
        secondArticle.addTag(new Tags(tags));

        List<Article> articles = articleRepository.findAllByTagNameIn(List.of("SPRING"));

        assertThat(articles).hasSize(2);
    }
}
