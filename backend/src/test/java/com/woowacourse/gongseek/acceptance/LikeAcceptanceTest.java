package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.토론_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class LikeAcceptanceTest extends AcceptanceTest {

    @Test
    void 게시물을_추천한다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 엑세스토큰.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/articles/{articleId}/like", 게시물.getId())
                .then().log().all()
                .extract();

        //then
        LikeResponse likeResponse = response.as(LikeResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(likeResponse.getIsLike()).isTrue(),
                () -> assertThat(likeResponse.getLikeCount()).isEqualTo(1)
        );
    }

    @Test
    void 게시물_추천을_취소한다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 엑세스토큰.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/articles/{articleId}/like", 게시물.getId())
                .then().log().all()
                .extract();
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 엑세스토큰.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/articles/{articleId}/unlike", 게시물.getId())
                .then().log().all()
                .extract();

        //then
        LikeResponse likeResponse = response.as(LikeResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(likeResponse.getIsLike()).isFalse(),
                () -> assertThat(likeResponse.getLikeCount()).isEqualTo(0)
        );
    }
}
