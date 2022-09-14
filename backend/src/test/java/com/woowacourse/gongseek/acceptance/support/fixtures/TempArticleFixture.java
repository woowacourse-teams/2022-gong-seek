package com.woowacourse.gongseek.acceptance.support.fixtures;

import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TempArticleFixture {

    public static ExtractableResponse<Response> 임시_게시물을_등록한다(AccessTokenResponse tokenResponse,
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
}
