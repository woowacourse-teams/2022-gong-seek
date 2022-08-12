package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.common.DatabaseCleaner;
import com.woowacourse.gongseek.member.application.Encryptor;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.tag.exception.ExceededTagsException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private Encryptor encryptor;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("slo", "hanull", "avatar.com"));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

    @Transactional
    @Test
    void 회원이_기명_게시글을_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);

        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);
        Article foundArticle = articleRepository.findById(articleIdResponse.getId())
                .orElse(null);

        assertAll(
                () -> assertThat(articleIdResponse.getId()).isNotNull(),
                () -> assertThat(foundArticle.getArticleTags().getArticleTags()).hasSize(1)
        );
    }

    @Transactional
    @Test
    void 회원이_익명_게시글을_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);
        Article foundArticle = articleRepository.findById(articleIdResponse.getId())
                .orElse(null);

        assertAll(
                () -> assertThat(articleIdResponse.getId()).isNotNull(),
                () -> assertThat(foundArticle.getArticleTags().getArticleTags()).hasSize(1)
        );
    }

    @Test
    void 회원이_중복된_해시태그로_게시글을_저장하면_예외가_발생한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring", "spring"), true);

        assertThatThrownBy(() -> articleService.save(new LoginMember(member.getId()), articleRequest))
                .isInstanceOf(DuplicateTagException.class)
                .hasMessage("해시태그 이름은 중복될 수 없습니다.");
    }

    @Test
    void 회원이_게시글에_해시태그를_5개_초과하여_저장하면_예외가_발생한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("aa", "bb", "cc", "dd", "ee", "ff"), true);

        assertThatThrownBy(() -> articleService.save(new LoginMember(member.getId()), articleRequest))
                .isInstanceOf(ExceededTagsException.class)
                .hasMessage("해시태그는 한 게시글 당 최대 5개입니다.");
    }

    @Transactional
    @Test
    void 회원이_게시글에_해시태그를_달지_않고_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of(), true);
        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);
        Article foundArticle = articleRepository.findById(articleIdResponse.getId())
                .orElse(null);

        assertAll(
                () -> assertThat(articleIdResponse.getId()).isNotNull(),
                () -> assertThat(foundArticle.getArticleTags().getArticleTags()).hasSize(0)
        );
    }

    @Test
    void 회원이_같은_해시태그로_게시글을_여러개_작성해도_해시태그는_하나이다() {
        ArticleRequest firstArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleRequest secondArticleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        articleService.save(new LoginMember(member.getId()), firstArticleRequest);
        articleService.save(new LoginMember(member.getId()), secondArticleRequest);

        List<Tag> tags = tagRepository.findAll();
        assertAll(
                () -> assertThat(tags).hasSize(1),
                () -> assertThat(tags.get(0).getName().getValue()).isEqualTo("SPRING")
        );
    }

    @Test
    void 회원이_익명_게시글을_저장하면_익명_회원이_저장된다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);

        Long id = member.getId();
        String cipherText = encryptor.encrypt(String.valueOf(id));
        Member anonymousMember = memberRepository.findByGithubId(cipherText)
                .orElseThrow();
        Article article = articleRepository.findById(articleIdResponse.getId())
                .orElseThrow();

        assertAll(
                () -> assertThat(anonymousMember)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(new Member("익명", cipherText,
                                "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png")),
                () -> assertThat(article.getMember().getId()).isEqualTo(anonymousMember.getId())
        );
    }

    @Test
    void 회원이_익명_게시글을_2번_저장한다() {
        String cipherText = encryptor.encrypt(String.valueOf(member.getId()));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse firstArticleId = articleService.save(new LoginMember(member.getId()), articleRequest);
        ArticleIdResponse secondArticleId = articleService.save(new LoginMember(member.getId()), articleRequest);

        Member anonymousMember = memberRepository.findByGithubId(cipherText)
                .orElseThrow();
        Article firstArticle = articleRepository.findById(firstArticleId.getId())
                .orElseThrow();
        Article secondArticle = articleRepository.findById(secondArticleId.getId())
                .orElseThrow();

        assertAll(
                () -> assertThat(secondArticle.getMember().getId()).isEqualTo(anonymousMember.getId()),
                () -> assertThat(firstArticle.getMember().getId()).isEqualTo(secondArticle.getMember().getId())
        );
    }

    @Test
    void 비회원은_게시글을_저장할_수_없다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);

        assertThatThrownBy(() -> articleService.save(new GuestMember(), articleRequest))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 작성자인_회원이_기명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo"),
                () -> assertThat(articleResponse.isAuthor()).isTrue()
        );
    }

    @Test
    void 작성자인_회원이_익명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(articleResponse.isAuthor()).isTrue()
        );
    }

    @Test
    void 작성자가_아닌_회원이_기명_게시글을_조회한다() {
        Member notAuthorMember = memberRepository.save(new Member("judy", "juriring", "avatar.com"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(this.member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(notAuthorMember.getId()),
                savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo"),
                () -> assertThat(articleResponse.isAuthor()).isFalse()
        );
    }

    @Test
    void 작성자가_아닌_회원이_익명_게시글을_조회한다() {
        Member notAuthorMember = memberRepository.save(new Member("judy", "juriring", "avatar.com"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(notAuthorMember.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(articleResponse.isAuthor()).isFalse()
        );
    }

    @Test
    void 비회원이_기명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo"),
                () -> assertThat(articleResponse.isAuthor()).isFalse()
        );
    }

    @Test
    void 비회원이_익명_게시글을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(articleResponse.isAuthor()).isFalse()
        );
    }

    @Test
    void 게시글을_조회하면_조회수가_올라간다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

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

    @Transactional
    @Test
    void 작성자인_회원이_기명_게시글을_수정한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));
        articleService.update(loginMember, request, savedArticle.getId());

        ArticleResponse response = articleService.getOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(response.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(response.getTag()).hasSize(1),
                () -> assertThat(response.getTag().get(0)).isEqualTo("JAVA"),
                () -> assertThat(response.getContent()).isEqualTo(request.getContent()),
                () -> assertThat(response.getAuthor().getName()).isEqualTo("slo")
        );
    }

    @Test
    void 작성자인_회원이_익명_게시글을_수정한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));
        articleService.update(loginMember, request, savedArticle.getId());

        ArticleResponse response = articleService.getOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(response.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(response.getTag()).hasSize(1),
                () -> assertThat(response.getTag().get(0)).isEqualTo("JAVA"),
                () -> assertThat(response.getContent()).isEqualTo(request.getContent()),
                () -> assertThat(response.getAuthor().getName()).isEqualTo("익명")
        );
    }

    @Test
    void 작성자가_아닌_회원이_게시글을_수정하면_예외가_발생한다() {
        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));

        assertThatThrownBy(() -> articleService.update(noAuthorMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 비회원이_게시글을_수정하면_예외가_발생한다() {
        AppMember guestMember = new GuestMember();
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.", List.of("JAVA"));

        assertThatThrownBy(() -> articleService.update(guestMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 작성자인_회원이_기명_게시글을_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.getOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자인_회원이_익명_게시글을_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), true);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.getOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자가_아닌_회원이_게시글을_삭제하면_예외가_발생한다() {
        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());

        assertThatThrownBy(() -> articleService.delete(noAuthorMember, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 비회원이_게시글을_삭제하면_예외가_발생한다() {
        AppMember guestMember = new GuestMember();
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(),
                List.of("Spring"), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        assertThatThrownBy(() -> articleService.delete(guestMember, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
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

        ArticlePageResponse response = articleService.getAll(null, 0, Category.QUESTION.getValue(), "latest", 10,
                loginMember);
        List<ArticlePreviewResponse> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(10),
                () -> assertThat(response.isHasNext()).isEqualTo(true)
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

        ArticlePageResponse response = articleService.getAll(10L, 0, Category.QUESTION.getValue(), "latest", 10,
                loginMember);
        List<ArticlePreviewResponse> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(9),
                () -> assertThat(responses.get(0).getId()).isEqualTo(9L),
                () -> assertThat(response.isHasNext()).isEqualTo(false)
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {0})
    void 페이지가_10개씩_조회된_후_더이상_조회할_페이지가_없으면_hasNext는_false가_된다(Integer cursorViews) {
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
                "latest", 10, loginMember);
        List<ArticlePreviewResponse> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(10),
                () -> assertThat(response.isHasNext()).isEqualTo(false)
        );
    }

    @Test
    void 공백으로_게시글을_검색한_경우_빈_값이_나온다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticlePageResponse articlePageResponse = articleService.search(null, 1, " ", loginMember);

        assertAll(
                () -> assertThat(articlePageResponse.getArticles()).hasSize(0),
                () -> assertThat(articlePageResponse.isHasNext()).isFalse()
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

        ArticlePageResponse articlePageResponse = articleService.search(null, 10, "질문", loginMember);

        assertAll(
                () -> assertThat(articlePageResponse.getArticles()).hasSize(10),
                () -> assertThat(articlePageResponse.isHasNext()).isFalse()
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

        ArticlePageResponse firstPageResponse = articleService.search(null, 10, "질문", loginMember);
        ArticlePageResponse secondPageResponse = articleService.search(
                firstPageResponse.getArticles().get(9).getId(), 10, "질문", loginMember);

        assertAll(
                () -> assertThat(firstPageResponse.getArticles()).hasSize(10),
                () -> assertThat(firstPageResponse.isHasNext()).isTrue(),
                () -> assertThat(secondPageResponse.getArticles()).hasSize(10),
                () -> assertThat(secondPageResponse.isHasNext()).isFalse()
        );
    }
}
