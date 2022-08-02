package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
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
import com.woowacourse.gongseek.commons.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
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

    @Test
    void 회원은_기명_게시물을_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);

        assertThat(articleIdResponse.getId()).isNotNull();
    }

    @Test
    void 회원은_익명_게시물을_저장한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), true);
        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);

        assertThat(articleIdResponse.getId()).isNotNull();
    }

    @Test
    void 비회원은_게시물을_저장할_수_없다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);

        assertThatThrownBy(() -> articleService.save(new GuestMember(), articleRequest))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 로그인을한_사용자가_기명_게시물을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo")
        );
    }

    @Test
    void 로그인을한_사용자가_익명_게시물을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), true);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명")
        );
    }

    @Test
    void 로그인을_안한_사용자가_기명_게시물을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("slo")
        );
    }

    @Test
    void 로그인을_안한_사용자가_익명_게시물을_조회한다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), true);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull(),
                () -> assertThat(articleResponse.getAuthor().getName()).isEqualTo("익명")
        );
    }

    @Test
    void 게시물을_조회하면_조회수가_올라간다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        articleService.getOne(new GuestMember(), savedArticle.getId());
        ArticleResponse articleResponse = articleService.getOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getViews()).isEqualTo(2),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull()
        );
    }

    @Test
    void 작성자가_기명_게시물을_수정한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.");
        articleService.update(loginMember, request, savedArticle.getId());

        ArticleResponse response = articleService.getOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(response.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(response.getContent()).isEqualTo(request.getContent()),
                () -> assertThat(response.getAuthor().getName()).isEqualTo("slo")
        );
    }

    @Test
    void 작성자가_익명_게시물을_수정한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), true);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.");
        articleService.update(loginMember, request, savedArticle.getId());

        ArticleResponse response = articleService.getOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(response.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(response.getContent()).isEqualTo(request.getContent()),
                () -> assertThat(response.getAuthor().getName()).isEqualTo("익명")
        );
    }

    @Test
    void 작성자가_아닌_사용자가_게시물을_수정하면_예외가_발생한다() {
        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.");

        assertThatThrownBy(() -> articleService.update(noAuthorMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 로그인을_안한_사용자가_게시물을_수정하면_예외가_발생한다() {
        AppMember guestMember = new GuestMember();
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.");

        assertThatThrownBy(() -> articleService.update(guestMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 작성자가_기명_게시물을_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.getOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자가_익명_게시물을_삭제한다() {
        AppMember loginMember = new LoginMember(member.getId());
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), true);
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.getOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자가_아닌_사용자가_게시물을_삭제하면_예외가_발생한다() {
        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());

        assertThatThrownBy(() -> articleService.delete(noAuthorMember, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 로그인을_안한_사용자가_게시물을_삭제하면_예외가_발생한다() {
        AppMember guestMember = new GuestMember();
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        assertThatThrownBy(() -> articleService.delete(guestMember, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 페이지가_10개씩_조회된다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            articles.add(
                    new Article(articleRequest.getTitle() + i, articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }
        articleRepository.saveAll(articles);

        ArticlePageResponse response = articleService.getAll(null, 0, Category.QUESTION.getValue(), "latest", 10);
        List<ArticlePreviewResponse> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(10),
                () -> assertThat(response.isHasNext()).isEqualTo(true)
        );
    }

    @Test
    void 요청으로_들어온_페이지ID_다음부터_반환해준다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            articles.add(
                    new Article(articleRequest.getTitle() + i, articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }
        articleRepository.saveAll(articles);

        ArticlePageResponse response = articleService.getAll(10L, 0, Category.QUESTION.getValue(), "latest", 10);
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
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(
                    new Article(articleRequest.getTitle(), articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }
        articleRepository.saveAll(articles);

        ArticlePageResponse response = articleService.getAll(null, cursorViews, Category.QUESTION.getValue(),
                "latest", 10);
        List<ArticlePreviewResponse> responses = response.getArticles();

        assertAll(
                () -> assertThat(responses).hasSize(10),
                () -> assertThat(response.isHasNext()).isEqualTo(false)
        );
    }

    @Test
    void 공백으로_게시물을_검색한_경우_빈_값이_나온다() {
        ArticlePageResponse articlePageResponse = articleService.search(null, 1, " ");

        assertAll(
                () -> assertThat(articlePageResponse.getArticles()).hasSize(0),
                () -> assertThat(articlePageResponse.isHasNext()).isFalse()
        );
    }

    @Test
    void 페이지가_10개씩_검색된_후_더이상_조회할_페이지가_없으면_hasNext가_false가_된다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        for (int i = 0; i < 10; i++) {
            articleRepository.save(
                    new Article(articleRequest.getTitle(), articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }

        ArticlePageResponse articlePageResponse = articleService.search(null, 10, "질문");

        assertAll(
                () -> assertThat(articlePageResponse.getArticles()).hasSize(10),
                () -> assertThat(articlePageResponse.isHasNext()).isFalse()
        );
    }

    @Test
    void 검색할_때_무한_스크롤이_가능하다() {
        ArticleRequest articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue(), false);
        for (int i = 0; i < 20; i++) {
            articleRepository.save(
                    new Article(articleRequest.getTitle(), articleRequest.getContent(), Category.QUESTION, member,
                            false));
        }

        ArticlePageResponse firstPageResponse = articleService.search(null, 10, "질문");
        ArticlePageResponse secondPageResponse = articleService.search(
                firstPageResponse.getArticles().get(9).getId(), 10, "질문");

        assertAll(
                () -> assertThat(firstPageResponse.getArticles()).hasSize(10),
                () -> assertThat(firstPageResponse.isHasNext()).isTrue(),
                () -> assertThat(secondPageResponse.getArticles()).hasSize(10),
                () -> assertThat(secondPageResponse.isHasNext()).isFalse()
        );
    }
}
