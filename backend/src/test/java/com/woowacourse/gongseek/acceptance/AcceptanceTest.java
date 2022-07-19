package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;

import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        RestAssured.port = port;
    }

    OAuthLoginUrlResponse 로그인_URL을_얻는다() {
        return RestAssured
                .given().log().all()
                .when()
                .get("/api/auth/github")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OAuthLoginUrlResponse.class);
    }

    TokenResponse 로그인을_한다() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new OAuthCodeRequest(주디.getCode()))
                .when()
                .post("/api/auth/fake/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);
    }
}
