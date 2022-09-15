package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인_URL을_얻는다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_하여_상태를_반환한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.토큰을_재발급한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.기론;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_할_수_있다() {
        // given
        OAuthLoginUrlResponse urlResponse = 로그인_URL을_얻는다();

        //when
        ExtractableResponse<Response> response = 로그인을_하여_상태를_반환한다(기론);
        AccessTokenResponse tokenResponse = response.as(AccessTokenResponse.class);
        //then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.SET_COOKIE)).isNotNull(),
                () -> assertThat(urlResponse.getUrl()).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );
    }

    @Test
    void 로그인을_하면_쿠키에_리프레시토큰을_담고_바디에는_엑세스토큰을_담아서_준다() {
        //given

        //when
        ExtractableResponse<Response> response = 로그인을_하여_상태를_반환한다(기론);
        AccessTokenResponse tokenResponse = response.as(AccessTokenResponse.class);

        //then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.SET_COOKIE)).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );

    }

    @Test
    void 리프레시토큰을_재발급할때_유효하면서_유효시간이_지난_엑세스토큰은_정상발급한다() {
        //given
        ExtractableResponse<Response> login = 로그인을_하여_상태를_반환한다(기론);
        AccessTokenResponse accessTokenResponse = login.as(AccessTokenResponse.class);
        //when
        ExtractableResponse<Response> response = 토큰을_재발급한다(login.cookie("refreshToken"),
                accessTokenResponse.getAccessToken());
        AccessTokenResponse tokenResponse = response.as(AccessTokenResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.header(HttpHeaders.SET_COOKIE)).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );
    }

    @Test
    void 리프레시토큰을_재발급할때_유효하지않으면_예외가_발생한다() {
        //given
        ExtractableResponse<Response> login = 로그인을_하여_상태를_반환한다(기론);

        //when
        ExtractableResponse<Response> response = 토큰을_재발급한다(login.cookie("refreshToken"),
                "invalidAcessToken");
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1005"),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("엑세스 토큰이 유효하지 않습니다.")
        );
    }
}
