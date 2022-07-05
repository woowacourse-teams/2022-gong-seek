package com.woowacourse.gongseek.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Disabled
public class ArticleAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_질문을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("code")
                .when()
                .post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @Test
    void 유저가_깃허브로_로그인을_하고_토론을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("code")
                .when()
                .post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "discussion"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }
}

