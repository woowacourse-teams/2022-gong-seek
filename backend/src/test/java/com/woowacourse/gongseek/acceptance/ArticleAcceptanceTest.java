package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_게시글을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new OAuthCodeRequest(주디.getCode()))
                .when()
                .post("/api/auth/fake/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_로그인을_하지않고_게시글을_등록할_수_없다() {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
