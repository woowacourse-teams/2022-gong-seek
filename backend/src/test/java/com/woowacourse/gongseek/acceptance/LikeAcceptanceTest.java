package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인_후_게시물을_조회한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.토론_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.LikeFixtures.게시물_추천을_취소한다;
import static com.woowacourse.gongseek.acceptance.support.LikeFixtures.게시물을_추천한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class LikeAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인_후_게시물을_추천한다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        ExtractableResponse<Response> response = 게시물을_추천한다(엑세스토큰, 게시물);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 로그인_후_게시물_추천을_취소한다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        게시물을_추천한다(엑세스토큰, 게시물);
        ExtractableResponse<Response> response = 게시물_추천을_취소한다(엑세스토큰, 게시물);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 게시물을_추천하면_추천수가_올라간다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        게시물을_추천한다(엑세스토큰, 게시물);
        게시물을_추천한다(로그인을_한다(슬로), 게시물);
        ArticleResponse response = 로그인_후_게시물을_조회한다(엑세스토큰, 게시물).as(ArticleResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getIsLike()).isTrue(),
                () -> assertThat(response.getLikeCount()).isEqualTo(2)
        );
    }

    @Test
    void 게시물_추천을_취소하면_추천수가_내려간다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);
        게시물을_추천한다(엑세스토큰, 게시물);
        게시물을_추천한다(로그인을_한다(슬로), 게시물);

        //when
        게시물_추천을_취소한다(엑세스토큰, 게시물);
        ArticleResponse response = 로그인_후_게시물을_조회한다(엑세스토큰, 게시물).as(ArticleResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getIsLike()).isFalse(),
                () -> assertThat(response.getLikeCount()).isEqualTo(1)
        );
    }

    @Test
    void 게시물_추천은_한_번만_할_수_있다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        게시물을_추천한다(엑세스토큰, 게시물);
        게시물을_추천한다(엑세스토큰, 게시물);
        ArticleResponse response = 로그인_후_게시물을_조회한다(엑세스토큰, 게시물).as(ArticleResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getIsLike()).isTrue(),
                () -> assertThat(response.getLikeCount()).isEqualTo(1)
        );
    }

    @Test
    void 게시물_추천_취소는_한_번만_할_수_있다() {
        //given
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시물 = 토론_게시물을_등록한다(엑세스토큰);

        //when
        게시물을_추천한다(엑세스토큰, 게시물);
        게시물_추천을_취소한다(엑세스토큰, 게시물);
        게시물_추천을_취소한다(엑세스토큰, 게시물);
        ArticleResponse response = 로그인_후_게시물을_조회한다(엑세스토큰, 게시물).as(ArticleResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getIsLike()).isFalse(),
                () -> assertThat(response.getLikeCount()).isEqualTo(0)
        );
    }
}
