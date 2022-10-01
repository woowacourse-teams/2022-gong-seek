package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
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
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

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
    private LikeRepository likeRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteHistoryRepository voteHistoryRepository;

    @Autowired
    private VoteItemRepository voteItemRepository;

    @Autowired
    private CommentRepository commentRepository;

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
        Slice<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "",
                PageRequest.ofSize(5));

        assertThat(articles).isEmpty();
    }

    @Test
    void 게시글을_5개씩_조회한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        Slice<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views",
                PageRequest.ofSize(5));

        assertThat(articles.getContent()).hasSize(5);
    }

    @ParameterizedTest
    @CsvSource({"all, 2", "question, 1", "discussion, 1"})
    void 카테고리별로_게시글을_조회한다(String category, int expectedSize) {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.DISCUSSION, member, false));

        Slice<Article> articles = articleRepository.findAllByPage(null, 0, category, "views", PageRequest.ofSize(5));

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

        Slice<Article> articles = articleRepository.findAllByPage(null, 0, Category.QUESTION.getValue(), "views",
                PageRequest.ofSize(10));

        assertThat(articles.getContent()).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
    }

    @Test
    void 게시글을_최신순으로_조회한다() {
        Article thirdArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article firstArticle = articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        Slice<Article> articles = articleRepository.findAllByPage(null, null, Category.QUESTION.getValue(), "latest",
                PageRequest.ofSize(3));

        assertThat(articles.getContent()).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle));
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
    @ValueSource(strings = {"this is wooteco", "is", "this", "wooteco"})
    void 제목으로_게시글을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am judy", "hello", Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = articleRepository.searchByContainingText(null, searchText, 0L,
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wow", "w"})
    void 띄어쓰기와_대소문자_관계_없이_내용으로_게시글을_검색한다(String searchText) {
        Article article = articleRepository.save(
                new Article("this is wooteco", "wow", Category.QUESTION, member, false));
        articleRepository.save(new Article("i am 주디", "hello", Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = articleRepository.searchByContainingText(null, searchText, 0L,
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(1);
    }

    @Test
    void 게시글을_5개씩_검색한다() {
        for (int i = 0; i < 5; i++) {
            articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        }
        Slice<ArticlePreviewDto> articles = articleRepository.searchByContainingText(null, "title", 0L,
                PageRequest.ofSize(5));

        assertThat(articles.getContent()).hasSize(5);
    }

    @Test
    void 게시글이_없을_때_검색하면_빈_값을_반환한다() {
        Slice<ArticlePreviewDto> articles = articleRepository.searchByContainingText(null, "empty", 0L,
                PageRequest.ofSize(5));

        assertThat(articles.getContent()).isEmpty();
    }

    @Test
    void 유저_이름을_이용하여_게시글을_검색한다() {
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        articleRepository.save(new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        Slice<ArticlePreviewDto> articles = articleRepository.searchByAuthor(null, member.getName(), member.getId(),
                PageRequest.ofSize(2));

        assertThat(articles.getContent()).hasSize(2);
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
        List<Long> tagIds = tagRepository.saveAll(tags).stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        article.addTag(new Tags(tags));

        boolean firstResult = articleRepository.existsArticleByTagId(tagIds.get(0));
        boolean secondResult = articleRepository.existsArticleByTagId(tagIds.get(1));
        boolean thirdResult = articleRepository.existsArticleByTagId(999L);

        assertAll(
                () -> assertThat(firstResult).isTrue(),
                () -> assertThat(secondResult).isTrue(),
                () -> assertThat(thirdResult).isFalse()
        );
    }

    @Test
    void 게시글을_추천순으로_조회하고_다음_데이터가_존재하지_않는다() {
        Article firstArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article secondArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));
        Article thirdArticle = articleRepository.save(
                new Article(TITLE, CONTENT, Category.QUESTION, member, false));

        likeRepository.save(new Like(firstArticle, member));
        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));
        likeRepository.save(new Like(firstArticle, newMember));
        likeRepository.save(new Like(secondArticle, member));

        Slice<Article> articles = articleRepository.findAllByLikes(null, null, Category.QUESTION.getValue(),
                PageRequest.ofSize(5));

        assertAll(
                () -> assertThat(articles.getContent()).isEqualTo(List.of(firstArticle, secondArticle, thirdArticle)),
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

        likeRepository.save(new Like(firstArticle, member));
        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));
        likeRepository.save(new Like(firstArticle, newMember));
        likeRepository.save(new Like(secondArticle, member));

        Slice<Article> articles = articleRepository.findAllByLikes(null, null, Category.QUESTION.getValue(),
                PageRequest.ofSize(2));

        assertAll(
                () -> assertThat(articles.getContent()).isEqualTo(List.of(firstArticle, secondArticle)),
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

        Slice<Article> articles = articleRepository.searchByTag(null, List.of(tag), PageRequest.ofSize(2));

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

        Slice<Article> articles = articleRepository.searchByTag(null, new ArrayList<>(), PageRequest.ofSize(2));

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

        Slice<Article> articles = articleRepository.searchByTag(null, List.of("spring", "java"), PageRequest.ofSize(3));

        assertThat(articles.getContent()).hasSize(3);
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

        List<MyPageArticleDto> myPageArticles = articleRepository.findAllByMemberIdWithCommentCount(
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
    void 게시글_단건_조회_시_작성자_투표생성여부_좋아요여부_좋아요갯수를_반환한다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.DISCUSSION, member, false));
        Tag firstTag = new Tag("Spring");
        Tag secondTag = new Tag("Rennon");
        tagRepository.saveAll(List.of(firstTag, secondTag));
        article.addTag(new Tags(List.of(firstTag, secondTag)));
        likeRepository.save(new Like(article, member));

        ArticleDto foundArticleDto = articleRepository.findByIdWithAll(article.getId(), member.getId()).get();

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
        Optional<ArticleDto> articleDto = articleRepository.findByIdWithAll(500L, member.getId());

        assertThat(articleDto).isEmpty();
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

    @Test
    void 게시글의_아이디로_태그를_찾을_수_있다() {
        Article article = articleRepository.save(
                new Article("title", "content", Category.DISCUSSION, member, false));
        Tag tag = tagRepository.save(new Tag("spring"));
        article.addTag(new Tags(List.of(tag)));

        testEntityManager.flush();
        testEntityManager.clear();

        List<String> tags = articleRepository.findTagNamesByArticleId(article.getId());

        assertThat(tags).isEqualTo(article.getTagNames());
    }
}
