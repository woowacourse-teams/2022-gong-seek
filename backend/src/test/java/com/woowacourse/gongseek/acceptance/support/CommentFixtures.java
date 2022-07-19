package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CommentFixtures {

    public static ExtractableResponse<Response> 댓글을_등록한다(TokenResponse tokenResponse,
                                                         ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content"))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();
    }

    public static List<CommentResponse> 댓글을_조회한다(TokenResponse tokenResponse,
                                                 ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/articles/{article_id}/comments")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath().getList(".", CommentResponse.class);
    }

    public static ExtractableResponse<Response> 댓글을_수정한다(TokenResponse tokenResponse,
                                                         List<CommentResponse> commentResponses) {
        return RestAssured
                .given().log().all()
                .pathParam("comment_id", commentResponses.get(0).getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("Update Content"))
                .when()
                .put("/api/articles/comments/{comment_id}")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글을_삭제한다(TokenResponse tokenResponse,
                                                         List<CommentResponse> commentResponses) {
        return RestAssured
                .given().log().all()
                .pathParam("comment_id", commentResponses.get(0).getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .delete("/api/articles/comments/{comment_id}")
                .then().log().all()
                .extract();
    }
}
