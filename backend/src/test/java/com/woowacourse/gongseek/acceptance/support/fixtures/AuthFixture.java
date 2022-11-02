package com.woowacourse.gongseek.acceptance.support.fixtures;

import com.woowacourse.gongseek.auth.application.dto.AccessTokenResponse;
import com.woowacourse.gongseek.auth.application.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.application.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class AuthFixture {

    public static AccessTokenResponse 로그인을_한다(GithubClientFixtures client) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new OAuthCodeRequest(client.getCode()))
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AccessTokenResponse.class);
    }

    public static OAuthLoginUrlResponse 로그인_URL을_얻는다() {
        return RestAssured
                .given().log().all()
                .when()
                .get("/api/auth/github")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OAuthLoginUrlResponse.class);
    }

    public static ExtractableResponse<Response> 로그인을_하여_상태를_반환한다(GithubClientFixtures client) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new OAuthCodeRequest(client.getCode()))
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 토큰을_재발급한다(String refreshToken, String accessToken) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .cookie("refreshToken", refreshToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/auth/refresh")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그아웃을_한다(String refreshToken) {
        return RestAssured
                .given().log().all()
                .cookie("refreshToken", refreshToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/auth/logout")
                .then().log().all()
                .extract();
    }
}
