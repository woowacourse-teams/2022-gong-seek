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

import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.Set;
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
                new VoteCreateRequest(Set.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteCreateResponse voteCreateResponse = response.as(VoteCreateResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(voteCreateResponse.getArticleId()).isEqualTo(articleId)
        );
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
                new VoteCreateRequest(Set.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
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
                new VoteCreateRequest(Set.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
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
                new VoteCreateRequest(Set.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
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
    void 비회원이_투표를_하면_투표수가_안_오른다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 토론_게시글을_기명으로_등록한다(tokenResponse).getId();

        투표를_생성한다(
                tokenResponse,
                articleId,
                new VoteCreateRequest(Set.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."), LocalDateTime.now().plusDays(2))
        );
        VoteResponse firstGetResponse = 투표를_조회한다(tokenResponse, articleId).as(VoteResponse.class);

        //when
        Long votedItemId = firstGetResponse.getVoteItems()
                .stream()
                .findFirst()
                .get()
                .getId();
        ErrorResponse response = 투표를_한다(new AccessTokenResponse(""), articleId,
                new SelectVoteItemIdRequest(votedItemId)).as(
                ErrorResponse.class);
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1004"),
                () -> assertThat(response.getMessage()).isEqualTo("토큰 타입이 올바르지 않습니다.")
        );
    }
}
