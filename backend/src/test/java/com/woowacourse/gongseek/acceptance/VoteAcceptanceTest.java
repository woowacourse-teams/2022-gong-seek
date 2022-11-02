package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.fixtures.ArticleFixture.토론_게시글을_기명으로_등록한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.ArticleFixture.토론_게시글을_익명으로_등록한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixture.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.VoteFixture.투표를_생성한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.VoteFixture.투표를_조회한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.VoteFixture.투표를_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.application.dto.AccessTokenResponse;
import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class VoteAcceptanceTest extends AcceptanceTest {

    @Test
    void 토론게시글에서_기명으로_투표를_생성한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        //when
        ExtractableResponse<Response> response = 투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteCreateResponse voteCreateResponse = response.as(VoteCreateResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(voteCreateResponse.getArticleId()).isEqualTo(articleId)
        );
    }

    @Test
    void 투표_아이템_수가_올바르지_않은_경우_5009_예외_코드를_반환한다() {
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        ExtractableResponse<Response> response = 투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );

        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo("5009");
    }

    @Test
    void 투표_아이템_수가_null_경우_0001_예외_코드를_반환한다() {
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        ExtractableResponse<Response> response = 투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(null, LocalDateTime.now().plusDays(2))
        );

        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo("0001");
    }

    @Test
    void 토론게시글에서_익명으로_투표를_생성한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_익명으로_등록한다(tokenResponse).getId();

        //when
        ExtractableResponse<Response> response = 투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteCreateResponse voteCreateResponse = response.as(VoteCreateResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(voteCreateResponse.getArticleId()).isEqualTo(articleId)
        );
    }

    @Test
    void 투표를_안한_사용자가_투표를_조회하면_선택한_투표_식별자는_null이_나온다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        투표를_생성한다(
                tokenResponse, articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );

        //when
        ExtractableResponse<Response> response = 투표를_조회한다(tokenResponse, articleId);
        VoteResponse voteResponse = response.as(VoteResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(voteResponse.getArticleId()).isEqualTo(articleId),
                () -> assertThat(voteResponse.getVotedItemId()).isNull(),
                () -> assertThat(voteResponse.getVoteItems()).hasSize(2)
        );
    }

    @Test
    void 투표를_한_사용자가_투표를_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteResponse firstGetResponse = 투표를_조회한다(tokenResponse, articleId).as(VoteResponse.class);

        //when
        Long votedItemId = firstGetResponse.getVoteItems()
                .stream()
                .findFirst()
                .get()
                .getId();
        ExtractableResponse<Response> response = 투표를_한다(tokenResponse, articleId,
                new SelectVoteItemIdRequest(votedItemId));
        VoteResponse voteResponse = 투표를_조회한다(tokenResponse, articleId).as(VoteResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(voteResponse.getArticleId()).isEqualTo(articleId),
                () -> assertThat(voteResponse.getVotedItemId()).isEqualTo(votedItemId),
                () -> assertThat(voteResponse.getVoteItems()).hasSize(2)
        );
    }

    @Test
    void 비회원이_투표를_조회할_수_있다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteResponse firstGetResponse = 투표를_조회한다(tokenResponse, articleId).as(VoteResponse.class);

        //when
        Long votedItemId = firstGetResponse.getVoteItems()
                .stream()
                .findFirst()
                .get()
                .getId();
        ExtractableResponse<Response> response = 투표를_한다(tokenResponse, articleId,
                new SelectVoteItemIdRequest(votedItemId));
        VoteResponse voteResponse = 투표를_조회한다(new AccessTokenResponse(null), articleId).as(VoteResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(voteResponse.getArticleId()).isEqualTo(articleId),
                () -> assertThat(voteResponse.getVotedItemId()).isNull(),
                () -> assertThat(voteResponse.getVoteItems()).hasSize(2)
        );
    }

    @Test
    void 비회원이_투표를_하면_투표수가_안_오른다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(List.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteResponse firstGetResponse = 투표를_조회한다(tokenResponse, articleId).as(VoteResponse.class);

        //when
        Long votedItemId = firstGetResponse.getVoteItems()
                .stream()
                .findFirst()
                .get()
                .getId();
        ErrorResponse response = 투표를_한다(new AccessTokenResponse(null), articleId,
                new SelectVoteItemIdRequest(votedItemId)).as(ErrorResponse.class);
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("2001"),
                () -> assertThat(response.getMessage()).contains("회원이 존재하지 않습니다.")
        );
    }
}
