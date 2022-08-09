package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class LikeFixtures {

    public static ExtractableResponse<Response> 게시물을_추천한다(TokenResponse accessToken,
                                                          ArticleIdResponse articleIdResponse) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/articles/{articleId}/like", articleIdResponse.getId())
                .then().log().all()
                .extract();
        return response;
    }

    public static ExtractableResponse<Response> 게시물_추천을_취소한다(TokenResponse 엑세스토큰, ArticleIdResponse 게시물) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 엑세스토큰.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/articles/{articleId}/unlike", 게시물.getId())
                .then().log().all()
                .extract();
    }
}
