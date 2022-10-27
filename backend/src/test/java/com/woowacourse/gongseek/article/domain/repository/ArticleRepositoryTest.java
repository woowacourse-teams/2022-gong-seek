package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPagePreviewDto;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
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
    private VoteRepository voteRepository;

    @Autowired
    private VoteHistoryRepository voteHistoryRepository;

    @Autowired
    private VoteItemRepository voteItemRepository;

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

    @ParameterizedTest
    @ValueSource(ints = {1, 9999, 10_000})
    void 질문_내용의_길이가_10000자까지_가능하다(int count) {
        String title = "질문합니다.";
        String content = "a".repeat(count);

        Article article = new Article(title, content, Category.QUESTION, member, false);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }

    @Test
    void 존재하지_않는_게시글_식별자로_단건_조회_시_Optional을_반환한다() {
        Optional<Article> article = articleRepository.findByIdWithAll(500L);

        assertThat(article).isEmpty();
    }

    @Test
    void 회원이_작성한_게시글들을_조회할_수_있다() {
        Article firstArticle = articleRepository.save(new Article("title1", "content1", Category.DISCUSSION, member, false));
        Article secondArticle = articleRepository.save(new Article("title1", "content1", Category.DISCUSSION, member, false));

        List<MyPagePreviewDto> myPagePreviewDtos = articleRepository.findAllByMemberIdWithCommentCount(
                member.getId());

        assertAll(
                () -> assertThat(myPagePreviewDtos).hasSize(2),
                () -> assertThat(myPagePreviewDtos.get(0).getId()).isEqualTo(firstArticle.getId()),
                () -> assertThat(myPagePreviewDtos.get(1).getId()).isEqualTo(secondArticle.getId()),
                () -> assertThat(myPagePreviewDtos.get(1).getCommentCount()).isEqualTo(0),
                () -> assertThat(myPagePreviewDtos.get(1).getCommentCount()).isEqualTo(0)
        );
    }

    @Test
    void 회원이_작성한_게시글들을_수정할_수_있다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));

        article.update("수정 제목", "내용 바꿉니다.", new Tags(new ArrayList<>()));
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

    @Transactional
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
        firstFoundArticle.update("title1", "content1", new Tags(List.of(updatedTag)));

        testEntityManager.flush();
        testEntityManager.clear();

        Article foundArticle = articleRepository.findById(article.getId()).get();

        assertAll(
                () -> assertThat(tagRepository.findAll()).hasSize(3),
                () -> assertThat(foundArticle.getArticleTags().getValue()).hasSize(1)
        );
    }

    @Test
    void 투표가_있는_토론게시글을_삭제한다() {
        Article article = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        voteRepository.save(new Vote(article, LocalDateTime.now().plusDays(3)));

        assertThat(voteRepository.findByArticleId(article.getId())).isNotEmpty();
        articleRepository.deleteById(article.getId());

        assertThat(voteRepository.findByArticleId(article.getId())).isEmpty();
    }
}
