package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인_URL을_얻는다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.기론;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_할_수_있다() {
        // given
        OAuthLoginUrlResponse urlResponse = 로그인_URL을_얻는다();

        //when
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);

        //then
        assertAll(
                () -> assertThat(urlResponse.getUrl()).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );
    }

    @Test
    void 로그인을_하면_쿠키에_리프레시토큰을_담고_바디에는_엑세스토큰을_담아서_준다() {
        //given
        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new OAuthCodeRequest(기론.getCode()))
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        AccessTokenResponse tokenResponse = response.as(AccessTokenResponse.class);

        //then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.SET_COOKIE)).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );

    }

    @Test
    void 리프래시토큰과_액새스토큰을_재발급_받는다() {
        //given
        //when
        ExtractableResponse<Response> response = 토큰을_재발급한다();
        AccessTokenResponse tokenResponse = response.as(AccessTokenResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.header(HttpHeaders.COOKIE)).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );
    }

    private ExtractableResponse<Response> 토큰을_재발급한다() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.SET_COOKIE, "refreshToken")
                .when()
                .get("/api/auth/refresh")
                .then().log().all()
                .extract();
    }
}
