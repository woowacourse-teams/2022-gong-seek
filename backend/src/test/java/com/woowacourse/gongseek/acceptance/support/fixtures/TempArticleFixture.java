package com.woowacourse.gongseek.acceptance.support.fixtures;

import com.woowacourse.gongseek.article.application.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.application.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TempArticleFixture {

    public static ExtractableResponse<Response> 임시_게시글을_등록한다(AccessTokenResponse tokenResponse,
                                                             ArticleRequest request) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/temp-articles")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 임시_게시글_단건_조회한다(AccessTokenResponse tokenResponse, long tempArticleId) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/temp-articles/{tempArticleId}", tempArticleId)
                .then().log().all()
                .extract();
    }
}
