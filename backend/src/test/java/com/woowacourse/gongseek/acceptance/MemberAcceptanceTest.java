package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.기명으로_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.익명으로_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.토론_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.기명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.익명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.MemberFixtures.내_정보를_조회한다;
import static com.woowacourse.gongseek.acceptance.support.MemberFixtures.내가_작성한_게시글들을_조회한다;
import static com.woowacourse.gongseek.acceptance.support.MemberFixtures.내가_작성한_댓글들을_조회한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.기론;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.레넌;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.common.exception.ErrorResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateRequest;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_정보를_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);

        // when
        ExtractableResponse<Response> response = 내_정보를_조회한다(tokenResponse);
        AuthorDto authorDto = response.as(AuthorDto.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(authorDto.getName()).isEqualTo(레넌.getName()),
                () -> assertThat(authorDto.getAvatarUrl()).isEqualTo(레넌.getAvatarUrl())
        );
    }

    @Test
    void 회원이_작성한_기명_게시글들을_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        토론_게시물을_등록한다(tokenResponse);

        // when
        ExtractableResponse<Response> response = 내가_작성한_게시글들을_조회한다(tokenResponse);
        MyPageArticlesResponse myPageArticlesResponse = response.as(MyPageArticlesResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageArticlesResponse.getArticles()).hasSize(1)
        );
    }

    @Test
    void 회원이_작성한_익명_기명_게시글을_모두_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        기명으로_게시물을_등록한다(tokenResponse, Category.QUESTION);
        익명으로_게시물을_등록한다(tokenResponse, Category.DISCUSSION);

        // when
        ExtractableResponse<Response> response = 내가_작성한_게시글들을_조회한다(tokenResponse);
        MyPageArticlesResponse myPageArticlesResponse = response.as(MyPageArticlesResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageArticlesResponse.getArticles()).hasSize(2)
        );
    }

    @Test
    void 회원이_작성한_익명_기명_댓글들을_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        ArticleIdResponse 게시글번호 = 토론_게시물을_등록한다(tokenResponse);
        기명으로_댓글을_등록한다(tokenResponse, 게시글번호);
        익명으로_댓글을_등록한다(tokenResponse, 게시글번호);

        // when
        ExtractableResponse<Response> response = 내가_작성한_댓글들을_조회한다(tokenResponse);
        MyPageCommentsResponse myPageCommentsResponse = response.as(MyPageCommentsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageCommentsResponse.getComments()).hasSize(2)
        );
    }

    @Test
    void 회원이_이름을_수정한다() {
        AccessTokenResponse tokenResponse = 로그인을_한다(기론);

        ExtractableResponse<Response> response = 이름을_수정한다(tokenResponse, "길동이");
        MemberUpdateResponse updateResponse = response.as(MemberUpdateResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(updateResponse.getName()).isEqualTo("길동이")
        );
    }

    private ExtractableResponse<Response> 이름을_수정한다(AccessTokenResponse tokenResponse, String name) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MemberUpdateRequest(name))
                .when()
                .patch("/api/members/me")
                .then().log().all()
                .extract();
    }

    @Test
    void 회원이_아닌_사람이_이름을_수정하려고하면_예외가_발생한다() {

        ExtractableResponse<Response> response = 이름을_수정한다(new AccessTokenResponse(null), "길동이");
        ErrorResponse updateResponse = response.as(ErrorResponse.class);

        assertAll(
                () -> assertThat(updateResponse.getErrorCode()).isEqualTo("2001"),
                () -> assertThat(updateResponse.getMessage()).isEqualTo("회원이 존재하지 않습니다.")
        );
    }
}
