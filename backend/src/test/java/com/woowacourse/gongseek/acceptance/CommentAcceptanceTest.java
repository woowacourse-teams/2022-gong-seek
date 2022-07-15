package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CommentAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_댓글을_등록할_수_있다() {
        //given
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

        ArticleIdResponse articleIdResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ArticleIdResponse.class);

        //when
        ExtractableResponse<Response> commentResponse = RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content"))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();

        //then
        assertThat(commentResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_로그인을_하지_않고_댓글을_등록할_수_없다() {
        ExtractableResponse<Response> commentResponse = RestAssured
                .given().log().all()
                .pathParam("article_id", 1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content"))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();

        //then
        assertThat(commentResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 댓글을_조회할_수_있다() {
        //given
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

        ArticleIdResponse articleIdResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ArticleIdResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content"))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();

        //when
        List<CommentResponse> commentResponses = RestAssured
                .given().log().all()
                .when()
                .get("/api/articles/{article_id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath().getList(".", CommentResponse.class);

        //then
        assertThat(commentResponses.size()).isEqualTo(1);
    }

    @Test
    void 댓글을_작성한_유저일_경우_댓글을_수정할_수_있다() {
        //given
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

        ArticleIdResponse articleIdResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ArticleIdResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content"))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();

        List<CommentResponse> commentResponses = RestAssured
                .given().log().all()
                .when()
                .get("/api/articles/{article_id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath().getList(".", CommentResponse.class);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .pathParam("comment_id", commentResponses.get(0).getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("Update Content"))
                .when()
                .put("/api/articles/{article_id}/comments/{comment_id}")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 댓글을_작성한_유저일_경우_댓글을_삭제할_수_있다() {
        //given
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

        ArticleIdResponse articleIdResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ArticleIdResponse.class);

        RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content"))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();

        List<CommentResponse> commentResponses = RestAssured
                .given().log().all()
                .when()
                .get("/api/articles/{article_id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath().getList(".", CommentResponse.class);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .pathParam("comment_id", commentResponses.get(0).getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .delete("/api/articles/{article_id}/comments/{comment_id}")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
