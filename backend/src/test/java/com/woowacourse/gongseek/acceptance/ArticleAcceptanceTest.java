package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_게시글을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);

        // when
        ExtractableResponse<Response> response = 게시물을_등록한다(tokenResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하지_않고_게시글을_등록할_수_없다() {
        // when
        ExtractableResponse<Response> response = 게시물을_등록한다(new TokenResponse(""));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 로그인_없이_게시물을_단건_조회할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_안한_유저가_게시물을_조회한다(articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        false,
                                        1,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 로그인을_하고_게시물을_단건_조회할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_조회한다(tokenResponse, articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        true,
                                        1,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 로그인_하지_않으면_게시물을_수정할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_안한_유저가_게시물을_수정한다(articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 게시물_작성자가_아니면_게시물을_수정할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_수정한다(new TokenResponse("abc"), articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 게시물_작성자는_게시물을_수정할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_수정한다(tokenResponse, articleIdResponse);
        ArticleUpdateResponse articleUpdateResponse = response.as(ArticleUpdateResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleUpdateResponse.getId()).isEqualTo(articleIdResponse.getId()),
                () -> assertThat(articleUpdateResponse.getCategory()).isEqualTo(Category.QUESTION)
        );
    }

    @Test
    void 게시물_작성자는_게시물을_삭제할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 게시물_작성자가_아니면_게시물을_삭제할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_삭제한다(new TokenResponse("abc"), articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> 게시물을_등록한다(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인_후_게시물을_삭제한다(TokenResponse tokenResponse,
                                                          ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/articles/{id}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }


    private ExtractableResponse<Response> 로그인_안한_유저가_게시물을_조회한다(ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .when()
                .get("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인_후_게시물을_조회한다(TokenResponse tokenResponse,
                                                          ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인_안한_유저가_게시물을_수정한다(ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleUpdateRequest("title2", "content2"))
                .when()
                .put("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 로그인_후_게시물을_수정한다(TokenResponse tokenResponse,
                                                          ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleUpdateRequest("title2", "content2"))
                .when()
                .put("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }
}

