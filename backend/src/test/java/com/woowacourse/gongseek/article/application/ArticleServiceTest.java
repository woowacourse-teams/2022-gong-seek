package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.application.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.application.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.application.dto.ArticleRequest;
import com.woowacourse.gongseek.article.application.dto.ArticleResponse;
import com.woowacourse.gongseek.article.application.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.TempArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.auth.application.dto.AppMember;
import com.woowacourse.gongseek.auth.application.dto.GuestMember;
import com.woowacourse.gongseek.auth.application.dto.LoginMember;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.like.application.LikeService;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.IntegrationTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.tag.exception.ExceededTagSizeException;
import com.woowacourse.gongseek.vote.application.VoteService;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleServiceTest extends IntegrationTest {

    private final Member member = new Member("slo", "hanull", "avatar.com");
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TempArticleRepository tempArticleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteItemRepository voteItemRepository;
    @Autowired
    private VoteHistoryRepository voteHistoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Transactional
    @Test
    void 회원이_기명_게시글을_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);

        ArticleIdResponse articleIdResponse = articleService.create(new LoginMember(member.getId()), articleRequest);
        Article foundArticle = articleRepository.findById(articleIdResponse.getId()).get();

        assertAll(
                () -> assertThat(articleIdResponse.getId()).isNotNull(),
                () -> assertThat(foundArticle.getArticleTags().getValue()).hasSize(1)
        );
    }

    @Transactional
    @Test
    void 회원이_익명_게시글을_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse articleIdResponse = articleService.create(new LoginMember(member.getId()), articleRequest);
        Article foundArticle = articleRepository.findById(articleIdResponse.getId()).get();

        assertAll(
                () -> assertThat(articleIdResponse.getId()).isNotNull(),
                () -> assertThat(foundArticle.getArticleTags().getValue()).hasSize(1)
        );
    }

    @Test
    void 회원이_중복된_해시태그로_게시글을_저장하면_예외가_발생한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring", "spring"), true);

        assertThatThrownBy(() -> articleService.create(new LoginMember(member.getId()), articleRequest))
                .isInstanceOf(DuplicateTagException.class)
                .hasMessage("해시태그 이름은 중복될 수 없습니다.");
    }

    @Test
    void 회원이_게시글에_해시태그를_5개_초과하여_저장하면_예외가_발생한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("aa", "bb", "cc", "dd", "ee", "ff"), true);

        assertThatThrownBy(() -> articleService.create(new LoginMember(member.getId()), articleRequest))
                .isInstanceOf(ExceededTagSizeException.class)
                .hasMessage("해시태그는 한 게시글 당 최대 5개입니다.");
    }

    @Transactional
    @Test
    void 회원이_게시글에_해시태그를_달지_않고_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of(), true);
        ArticleIdResponse articleIdResponse = articleService.create(new LoginMember(member.getId()), articleRequest);
        Article foundArticle = articleRepository.findById(articleIdResponse.getId()).get();

        assertAll(
                () -> assertThat(articleIdResponse.getId()).isNotNull(),
                () -> assertThat(foundArticle.getArticleTags().getValue()).hasSize(0)
        );
    }

    @Test
    void 회원이_같은_해시태그로_게시글을_여러개_작성해도_해시태그는_하나이다() {
        ArticleRequest firstArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleRequest secondArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        articleService.create(new LoginMember(member.getId()), firstArticleRequest);
        articleService.create(new LoginMember(member.getId()), secondArticleRequest);

        List<Tag> tags = tagRepository.findAll();
        assertAll(
                () -> assertThat(tags).hasSize(1),
                () -> assertThat(tags.get(0).getName()).isEqualTo("SPRING")
        );
    }

    @Test
    void 비회원은_게시글을_저장할_수_없다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);

        assertThatThrownBy(() -> articleService.create(new GuestMember(), articleRequest))
                .isExactlyInstanceOf(NotMemberException.class)
                .hasMessage("회원이 아니므로 권한이 없습니다.");
    }

    @Test
    void 작성자인_회원이_기명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo"),
                () -> assertThat(articleResponse.getIsAuthor()).isTrue()
        );
    }

    @Test
    void 작성자인_회원이_익명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(articleResponse.getIsAuthor()).isTrue()
        );
    }

    @Test
    void 작성자가_아닌_회원이_기명_게시글을_조회한다() {
        Member notAuthorMember = memberRepository.save(new Member("judy", "juriring", "avatar.com"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(this.member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(notAuthorMember.getId()),
                savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo"),
                () -> assertThat(articleResponse.getIsAuthor()).isFalse()
        );
    }

    @Test
    void 작성자가_아닌_회원이_익명_게시글을_조회한다() {
        Member notAuthorMember = memberRepository.save(new Member("judy", "juriring", "avatar.com"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(notAuthorMember.getId()),
                articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(articleResponse.getIsAuthor()).isFalse()
        );
    }

    @Test
    void 비회원이_기명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo"),
                () -> assertThat(articleResponse.getIsAuthor()).isFalse()
        );
    }

    @Test
    void 비회원이_익명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(articleResponse.getIsAuthor()).isFalse()
        );
    }

    @Test
    void 게시글을_조회하면_조회수가_올라간다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);

        articleService.getOne(new GuestMember(), savedArticle.getId());
        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getViews()).isEqualTo(2),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull()
        );
    }

    @Test
    void 작성자인_회원이_기명_게시글을_수정한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));
        articleService.update(loginMember, request, savedArticle.getId());

        ArticleResponse articleResponse = articleService.getOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(articleResponse.getTag()).hasSize(1),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("JAVA"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(request.getContent()),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo")
        );
    }

    @Test
    void 작성자인_회원이_익명_게시글을_수정한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.create(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));
        articleService.update(loginMember, request, savedArticle.getId());

        ArticleResponse articleResponse = articleService.getOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(articleResponse.getTag()).hasSize(1),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("JAVA"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(request.getContent()),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명")
        );
    }

    @Test
    void 작성자가_아닌_회원이_게시글을_수정하면_예외가_발생한다() {
        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));

        assertThatThrownBy(() -> articleService.update(noAuthorMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 비회원이_게시글을_수정하면_예외가_발생한다() {
        AppMember guestMember = new GuestMember();
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));

        assertThatThrownBy(() -> articleService.update(guestMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NotMemberException.class)
                .hasMessage("회원이 아니므로 권한이 없습니다.");
    }

    @Test
    void 회원이_게시글을_수정했을_때_해당_태그로_작성된_게시글이_없으면_태그도_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest firstArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring", "Java"), false);
        ArticleRequest secondArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Java"), false);
        ArticleIdResponse firstSavedArticle = articleService.create(loginMember, firstArticleRequest);
        articleService.create(loginMember, secondArticleRequest);

        articleService.update(loginMember, new ArticleUpdateRequest("하이", "하이", List.of("JAVA", "backend")),
                firstSavedArticle.getId());

        Article article = articleRepository.findByIdWithAll(firstSavedArticle.getId())
                .get();
        List<String> existTagNames = article.getTagNames();

        assertAll(
                () -> assertThat(existTagNames).hasSize(2),
                () -> assertThat(existTagNames.get(0)).isEqualTo("JAVA"),
                () -> assertThat(existTagNames.get(1)).isEqualTo("BACKEND"),
                () -> assertThat(tagRepository.findByNameIgnoreCase("SPRING")).isEmpty(),
                () -> assertThat(tagRepository.findByNameIgnoreCase("JAVA")).isNotEmpty(),
                () -> assertThat(tagRepository.findByNameIgnoreCase("BACKEND")).isNotEmpty()
        );
    }

    @Test
    void 작성자인_회원이_기명_게시글을_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.getOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessageContaining("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자인_회원이_익명_게시글을_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.create(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.getOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessageContaining("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자가_아닌_회원이_게시글을_삭제하면_예외가_발생한다() {
        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());

        assertThatThrownBy(() -> articleService.delete(noAuthorMember, savedArticle.getId()))
                .isExactlyInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 비회원이_게시글을_삭제하면_예외가_발생한다() {
        AppMember guestMember = new GuestMember();
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.create(new LoginMember(member.getId()), articleRequest);

        assertThatThrownBy(() -> articleService.delete(guestMember, savedArticle.getId()))
                .isExactlyInstanceOf(NotMemberException.class)
                .hasMessage("회원이 아니므로 권한이 없습니다.");
    }

    @Test
    void 회원이_게시글을_삭제했을_때_해당_태그로_작성된_게시글이_없으면_태그도_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest firstArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring", "Java"), false);
        ArticleRequest secondArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Java"), false);
        ArticleIdResponse firstSavedArticle = articleService.create(loginMember, firstArticleRequest);
        articleService.create(loginMember, secondArticleRequest);

        articleService.delete(loginMember, firstSavedArticle.getId());

        Optional<Article> article = articleRepository.findByIdWithAll(firstSavedArticle.getId());
        assertAll(
                () -> assertThat(article).isEmpty(),
                () -> assertThat(tagRepository.findByNameIgnoreCase("SPRING")).isEmpty(),
                () -> assertThat(tagRepository.findByNameIgnoreCase("JAVA")).isNotEmpty()
        );
    }

    @Test
    void 페이지가_10개씩_조회된다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            articles.add(
                    new Article(articleRequest.getTitle() + i, articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }
        articleRepository.saveAll(articles);

        ArticlePageResponse response = articleService.getAll(null, 0L, Category.QUESTION.getValue(), "latest",
                PageRequest.ofSize(10),
                loginMember);
        List<ArticlePreviewDto> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(10),
                () -> assertThat(response.getHasNext()).isEqualTo(true)
        );
    }

    @Test
    void 요청으로_들어온_페이지ID_다음부터_반환해준다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            articles.add(
                    new Article(articleRequest.getTitle() + i, articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }
        articleRepository.saveAll(articles);

        ArticlePageResponse response = articleService.getAll(10L, 0L, Category.QUESTION.getValue(), "latest",
                PageRequest.ofSize(10),
                loginMember);
        List<ArticlePreviewDto> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(9),
                () -> assertThat(responses.get(0).getId()).isEqualTo(9L),
                () -> assertThat(response.getHasNext()).isFalse()
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0L})
    void 페이지가_10개씩_조회된_후_더이상_조회할_페이지가_없으면_hasNext는_false가_된다(Long cursorViews) {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(
                    new Article(articleRequest.getTitle(), articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }
        articleRepository.saveAll(articles);

        ArticlePageResponse response = articleService.getAll(null, cursorViews, Category.QUESTION.getValue(),
                "latest", PageRequest.ofSize(10), loginMember);
        List<ArticlePreviewDto> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(10),
                () -> assertThat(response.getHasNext()).isFalse()
        );
    }

    @Test
    void 공백으로_게시글을_검색한_경우_빈_값이_나온다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticlePageResponse articlePageResponse = articleService.searchByText(null, PageRequest.ofSize(1), " ",
                loginMember);

        assertAll(
                () -> assertThat(articlePageResponse.getArticles()).isEmpty(),
                () -> assertThat(articlePageResponse.getHasNext()).isFalse()
        );
    }

    @Test
    void 페이지가_10개씩_검색된_후_더이상_조회할_페이지가_없으면_hasNext가_false가_된다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        for (int i = 0; i < 10; i++) {
            articleRepository.save(
                    new Article(articleRequest.getTitle(), articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }

        ArticlePageResponse articlePageResponse = articleService.searchByText(null, PageRequest.ofSize(10), "질문",
                loginMember);

        assertAll(
                () -> assertThat(articlePageResponse.getArticles()).hasSize(10),
                () -> assertThat(articlePageResponse.getHasNext()).isFalse()
        );
    }

    @Test
    void 검색할_때_무한_스크롤이_가능하다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        for (int i = 0; i < 20; i++) {
            articleRepository.save(
                    new Article(articleRequest.getTitle(), articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }

        ArticlePageResponse firstPageResponse = articleService.searchByText(null, PageRequest.ofSize(10), "질문",
                loginMember);
        ArticlePageResponse secondPageResponse = articleService.searchByText(
                firstPageResponse.getArticles().get(9).getId(), PageRequest.ofSize(10), "질문", loginMember);

        assertAll(
                () -> assertThat(firstPageResponse.getArticles()).hasSize(10),
                () -> assertThat(firstPageResponse.getHasNext()).isTrue(),
                () -> assertThat(secondPageResponse.getArticles()).hasSize(10),
                () -> assertThat(secondPageResponse.getHasNext()).isFalse()
        );
    }

    @Test
    void 이름으로_검색할_경우_작성자가_작성한_게시글이_조회된다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        for (int i = 0; i < 5; i++) {
            articleService.create(loginMember, articleRequest);
        }
        articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), List.of("Spring"),
                false);
        for (int i = 0; i < 5; i++) {
            articleService.create(loginMember, articleRequest);
        }
        Member newMember = memberRepository.save(new Member("slow", "slow", "avatarUrl"));
        loginMember = new LoginMember(newMember.getId());
        for (int i = 0; i < 5; i++) {
            articleService.create(loginMember, articleRequest);
        }

        ArticlePageResponse pageResponse = articleService.searchByAuthor(null, PageRequest.ofSize(15),
                this.member.getName(), loginMember);

        assertThat(pageResponse.getArticles()).hasSize(5);
    }

    @Test
    void 추천순으로_게시글이_조회된다() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(new Article("질문합니다.", "내용입니다~!", Category.QUESTION, member, false));
        }
        articleRepository.saveAll(articles);

        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));
        likeService.likeArticle(new LoginMember(member.getId()), 1L);

        likeService.likeArticle(new LoginMember(member.getId()), 2L);
        likeService.likeArticle(new LoginMember(newMember.getId()), 2L);

        likeService.likeArticle(new LoginMember(newMember.getId()), 3L);
        likeService.likeArticle(new LoginMember(member.getId()), 3L);

        ArticlePageResponse articlePageResponse = articleService.getAllByLikes(null, null, Category.QUESTION.getValue(),
                Pageable.ofSize(3), new LoginMember(member.getId()));
        List<Long> collect = articlePageResponse.getArticles()
                .stream()
                .map(ArticlePreviewDto::getId)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(articlePageResponse.getHasNext()).isTrue(),
                () -> assertThat(articlePageResponse.getArticles()).hasSize(3),
                () -> assertThat(collect).containsExactly(3L, 2L, 1L)
        );
    }

    @Test
    void 태그_한개로_검색할_경우_태그로_작성한_게시글들이_조회된다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        for (int i = 0; i < 15; i++) {
            articleService.create(loginMember, articleRequest);
        }

        ArticlePageResponse pageResponse = articleService.searchByTag(null, PageRequest.ofSize(15), "spring",
                loginMember);

        assertAll(
                () -> assertThat(pageResponse.getArticles()).hasSize(15),
                () -> assertThat(pageResponse.getHasNext()).isFalse()
        );
    }

    @Test
    void 다음_페이지를_조회할때도_추천순으로_게시글을_조회한다() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(new Article("질문합니다.", "내용입니다~!", Category.QUESTION, member, false));
        }
        articleRepository.saveAll(articles);

        Member newMember = memberRepository.save(new Member("newMember", "123", "www.avatar"));
        likeService.likeArticle(new LoginMember(member.getId()), 1L);

        likeService.likeArticle(new LoginMember(member.getId()), 2L);
        likeService.likeArticle(new LoginMember(newMember.getId()), 2L);

        likeService.likeArticle(new LoginMember(newMember.getId()), 3L);
        likeService.likeArticle(new LoginMember(member.getId()), 3L);

        ArticlePageResponse articlePageResponse = articleService.getAllByLikes(2L, 2L, Category.QUESTION.getValue(),
                Pageable.ofSize(2), new LoginMember(member.getId()));
        List<Long> collect = articlePageResponse.getArticles()
                .stream()
                .map(ArticlePreviewDto::getId)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(articlePageResponse.getHasNext()).isTrue(),
                () -> assertThat(articlePageResponse.getArticles()).hasSize(2),
                () -> assertThat(collect).containsExactly(1L, 10L)
        );
    }

    @Test
    void 태그_여러개로_검색할_경우_태그로_작성한_게시글들이_조회된다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest firstArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!",
                Category.QUESTION.getValue(),
                List.of("Spring"), true);
        for (int i = 0; i < 5; i++) {
            articleService.create(loginMember, firstArticleRequest);
        }
        ArticleRequest secondArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!",
                Category.QUESTION.getValue(),
                List.of("java"), true);
        for (int i = 0; i < 5; i++) {
            articleService.create(loginMember, secondArticleRequest);
        }

        ArticlePageResponse pageResponse = articleService.searchByTag(5L, PageRequest.ofSize(2), "spring,java",
                loginMember);

        assertAll(
                () -> assertThat(pageResponse.getArticles()).hasSize(2),
                () -> assertThat(pageResponse.getHasNext()).isTrue()
        );
    }

    @Test
    void 투표중인_토론게시글을_삭제한다() {
        Article article = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        LoginMember loginMember = new LoginMember(member.getId());
        voteService.create(loginMember, article.getId(),
                new VoteCreateRequest(List.of("A번", "B번", "C번"), LocalDateTime.now().plusDays(4)));

        voteService.doVote(article.getId(), loginMember, new SelectVoteItemIdRequest(1L));
        articleService.delete(loginMember, article.getId());
        assertAll(
                () -> assertThat(articleRepository.findById(article.getId())).isEmpty(),
                () -> assertThat(voteItemRepository.findAll()).isEmpty(),
                () -> assertThat(voteHistoryRepository.findAll()).isEmpty()
        );
    }

    @Test
    void 투표가_없는_토론게시글을_삭제한다() {
        Article article = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        LoginMember loginMember = new LoginMember(member.getId());
        articleService.delete(loginMember, article.getId());

        assertThat(articleRepository.findById(article.getId())).isEmpty();
    }
}
