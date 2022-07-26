package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private ArticleRequest articleRequest;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("slo", "hanull", "avatar.com"));
        articleRequest = new ArticleRequest("질문합니다.", "내용입니다~!", Category.QUESTION.getValue());
    }

    @Test
    void 회원은_게시물을_저장한다() {

        ArticleIdResponse articleIdResponse = articleService.save(new LoginMember(member.getId()), articleRequest);

        assertThat(articleIdResponse.getId()).isNotNull();
    }

    @Test
    void 비회원은_게시물을_저장할_수_없다() {

        assertThatThrownBy(() -> articleService.save(new GuestMember(), articleRequest))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 로그인을한_사용자가_게시물을_조회한다() {

        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.findOne(new LoginMember(member.getId()), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull()
        );
    }

    @Test
    void 로그인을_안한_사용자가_게시물을_조회한다() {

        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        ArticleResponse articleResponse = articleService.findOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull()
        );
    }

    @Test
    void 게시물을_조회하면_조회수가_올라간다() {

        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()), articleRequest);

        articleService.findOne(new GuestMember(), savedArticle.getId());
        ArticleResponse articleResponse = articleService.findOne(new GuestMember(), savedArticle.getId());

        assertAll(
                () -> assertThat(articleResponse.getTitle()).isEqualTo(articleRequest.getTitle()),
                () -> assertThat(articleResponse.getContent()).isEqualTo(articleRequest.getContent()),
                () -> assertThat(articleResponse.getViews()).isEqualTo(2),
                () -> assertThat(articleResponse.getCreatedAt()).isNotNull()
        );
    }

    @Test
    void 작성자가_게시물을_수정한다() {

        AppMember loginMember = new LoginMember(member.getId());
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.");
        articleService.update(loginMember,
                request,
                savedArticle.getId());

        ArticleResponse response = articleService.findOne(loginMember, savedArticle.getId());

        assertAll(
                () -> assertThat(response.getTitle()).isEqualTo(request.getTitle()),
                () -> assertThat(response.getContent()).isEqualTo(request.getContent())
        );
    }

    @Test
    void 작성자가_아닌_사용자가_게시물을_수정하면_예외가_발생한다() {

        Member noAuthor = memberRepository.save(
                new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
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
        ArticleIdResponse savedArticle = articleService.save(
                new LoginMember(member.getId()),
                articleRequest);
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 수정", "내용 수정합니다.");

        assertThatThrownBy(() -> articleService.update(guestMember, request, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 작성자가_게시물을_삭제한다() {

        AppMember loginMember = new LoginMember(member.getId());
        ArticleIdResponse savedArticle = articleService.save(loginMember, articleRequest);

        articleService.delete(loginMember, savedArticle.getId());

        assertThatThrownBy(() -> articleService.findOne(loginMember, savedArticle.getId()))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 작성자가_아닌_사용자가_게시물을_삭제하면_예외가_발생한다() {

        Member noAuthor = memberRepository.save(new Member("작성자아닌사람이름", "giithub", "www.avatar.cax"));
        ArticleIdResponse savedArticle = articleService.save(new LoginMember(member.getId()),
                articleRequest);
        AppMember noAuthorMember = new LoginMember(noAuthor.getId());

        assertThatThrownBy(() -> articleService.delete(noAuthorMember, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 로그인을_안한_사용자가_게시물을_삭제하면_예외가_발생한다() {

        AppMember guestMember = new GuestMember();
        ArticleIdResponse savedArticle = articleService.save(
                new LoginMember(member.getId()),
                articleRequest);

        assertThatThrownBy(() -> articleService.delete(guestMember, savedArticle.getId()))
                .isExactlyInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }
}
