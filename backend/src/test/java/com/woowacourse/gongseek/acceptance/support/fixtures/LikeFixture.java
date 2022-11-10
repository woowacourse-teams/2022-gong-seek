package com.woowacourse.gongseek.acceptance.support.fixtures;

import com.woowacourse.gongseek.article.application.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.application.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class LikeFixture {

    public static ExtractableResponse<Response> 게시글을_추천한다(AccessTokenResponse accessToken,
                                                          ArticleIdResponse article) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/articles/{articleId}/like", article.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 게시글_추천을_취소한다(AccessTokenResponse accessToken,
                                                             ArticleIdResponse article) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/articles/{articleId}/like", article.getId())
                .then().log().all()
                .extract();
    }
}
