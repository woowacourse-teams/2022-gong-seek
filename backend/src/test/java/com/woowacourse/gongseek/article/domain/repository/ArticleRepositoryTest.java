package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.like.domain.repository.LikeRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SuppressWarnings("NonAsciiCharacters")
@TestConstructor(autowireMode = AutowireMode.ALL)
@AllArgsConstructor
@RepositoryTest
class ArticleRepositoryTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private final Member member = new Member("slo", "hanull", "avatar.com");

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final VoteRepository voteRepository;
    private final VoteHistoryRepository voteHistoryRepository;
    private final VoteItemRepository voteItemRepository;
    private final TestEntityManager testEntityManager;

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
    void 회원이_작성한_게시글들을_조회할_수_있다() {
        Member otherMember = new Member("rennon", "brorae", "avatar.com");
        memberRepository.save(otherMember);
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, otherMember, false));

        List<Article> articles = articleRepository.findAllByMemberId(member.getId());

        assertThat(articles).containsExactly(firstArticle, secondArticle);
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

        Article secondFoundArticle = articleRepository.findById(article.getId()).get();

        assertAll(
                () -> assertThat(tagRepository.findAll()).hasSize(3),
                () -> assertThat(secondFoundArticle.getArticleTags().getValue()).hasSize(1)
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

    @Test
    void 투표중인_토론게시글을_삭제한다() {
        Article article = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        Vote vote = new Vote(article, LocalDateTime.now().plusDays(3));
        voteRepository.save(vote);
        VoteItem firstVoteItem = new VoteItem("A번", vote);
        VoteItem secondVoteItem = new VoteItem("B번", vote);
        VoteItem thirdVoteItem = new VoteItem("C번", vote);
        voteItemRepository.saveAll(List.of(firstVoteItem, secondVoteItem, thirdVoteItem));

        voteHistoryRepository.save(new VoteHistory(member, firstVoteItem));
        articleRepository.deleteById(article.getId());

        assertAll(
                () -> assertThat(voteRepository.findByArticleId(article.getId())).isEmpty(),
                () -> assertThat(voteHistoryRepository.findAll()).isEmpty()
        );
    }

    @Test
    void 게시글의_조회수를_증가시킨다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.DISCUSSION, member, false));

        testEntityManager.flush();
        testEntityManager.clear();

        articleRepository.addViews(article.getId());
        articleRepository.addViews(article.getId());
        articleRepository.addViews(article.getId());

        Article foundArticle = articleRepository.findById(article.getId()).get();
        int views = foundArticle.getViews();

        assertThat(views).isEqualTo(3L);
    }
}
