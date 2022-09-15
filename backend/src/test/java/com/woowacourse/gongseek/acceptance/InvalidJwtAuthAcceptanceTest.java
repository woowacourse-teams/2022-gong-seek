package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.fixtures.ArticleFixture.기명으로_게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixture.로그인을_하여_상태를_반환한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixture.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixture.토큰을_재발급한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.기론;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SuppressWarnings("NonAsciiCharacters")
@ActiveProfiles("invalid")
public class InvalidJwtAuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 시간이_만료된_엑세스토큰으로_요청을_하면_예외가_발생한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(기론);

        //when
        ErrorResponse errorResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION)
                .as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1005"),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("엑세스 토큰이 유효하지 않습니다.")
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
}
