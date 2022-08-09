package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class AuthFixtures {

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
}
