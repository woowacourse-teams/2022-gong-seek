package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentUpdateRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentsResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class CommentFixtures {

    private static ExtractableResponse<Response> 댓글을_등록한다(AccessTokenResponse tokenResponse,
                                                          ArticleIdResponse articleIdResponse,
                                                          boolean isAnonymous) {
        return RestAssured
                .given().log().all()
                .pathParam("article_id", articleIdResponse.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentRequest("content", isAnonymous))
                .when()
                .post("/api/articles/{article_id}/comments")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 기명으로_댓글을_등록한다(AccessTokenResponse tokenResponse,
                                                              ArticleIdResponse articleIdResponse) {
        return 댓글을_등록한다(tokenResponse, articleIdResponse, false);
    }

    public static ExtractableResponse<Response> 익명으로_댓글을_등록한다(AccessTokenResponse tokenResponse,
                                                              ArticleIdResponse articleIdResponse) {
        return 댓글을_등록한다(tokenResponse, articleIdResponse, true);
    }

    public static CommentsResponse 댓글을_조회한다(AccessTokenResponse tokenResponse,
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
                .as(CommentsResponse.class);
    }

    public static ExtractableResponse<Response> 댓글을_수정한다(AccessTokenResponse tokenResponse,
                                                         List<CommentResponse> commentResponses) {
        return RestAssured
                .given().log().all()
                .pathParam("comment_id", commentResponses.get(0).getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CommentUpdateRequest("Update Content"))
                .when()
                .put("/api/articles/comments/{comment_id}")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글을_삭제한다(AccessTokenResponse tokenResponse,
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
