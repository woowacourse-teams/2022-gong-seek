package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.기명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.레넌;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 내_정보를_조회한다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(레넌);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/members/me")
                .then().log().all()
                .extract();
        AuthorDto authorDto = response.as(AuthorDto.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(authorDto.getName()).isEqualTo(레넌.getName()),
                () -> assertThat(authorDto.getAvatarUrl()).isEqualTo(레넌.getAvatarUrl())
        );
    }

    @Test
    void 내가_작성한_게시글들을_조회한다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(레넌);
        게시물을_등록한다(tokenResponse);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/members/me/articles")
                .then().log().all()
                .extract();
        MyPageArticlesResponse myPageArticlesResponse = response.as(MyPageArticlesResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageArticlesResponse.getArticles()).size().isEqualTo(1)
        );
    }

    @Test
    void 내가_작성한_댓글들을_조회한다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(레넌);
        ArticleIdResponse 게시글번호 = 게시물을_등록한다(tokenResponse);
        기명으로_댓글을_등록한다(tokenResponse, 게시글번호);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/members/me/comments")
                .then().log().all()
                .extract();
        MyPageCommentsResponse myPageCommentsResponse = response.as(MyPageCommentsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageCommentsResponse.getComments()).size().isEqualTo(1)
        );
    }
}
