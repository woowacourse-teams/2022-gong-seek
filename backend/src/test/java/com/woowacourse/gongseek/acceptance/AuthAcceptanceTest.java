package com.woowacourse.gongseek.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.presentation.dto.OAuthUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Disabled
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_할_수_있다() {
        // given
        OAuthUrlResponse urlResponse = RestAssured
                .given().log().all()
                .when()
                .get("/api/auth/github")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OAuthUrlResponse.class);

        //when
        TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("code")
                .when()
                .post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);

        //then
        assertAll(
                () -> assertThat(urlResponse.getUrl()).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );
    }
}
