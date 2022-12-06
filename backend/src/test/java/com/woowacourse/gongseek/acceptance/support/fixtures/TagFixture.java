package com.woowacourse.gongseek.acceptance.support.fixtures;

import com.woowacourse.gongseek.auth.application.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;

public class TagFixture {

    public static ExtractableResponse<Response> 모든_해시태그를_조회한다(AccessTokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/tags")
                .then().log().all()
                .extract();
    }
}
