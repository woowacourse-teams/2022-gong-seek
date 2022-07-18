package com.woowacourse.gongseek.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
@Disabled
public class ArticleAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_질문을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다();

        // when
        ExtractableResponse<Response> response = 질문을_등록한다(tokenResponse);

        // then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @Test
    void 유저가_깃허브로_로그인을_하고_토론을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다();

        // when
        ExtractableResponse<Response> response = 토론을_등록한다(tokenResponse);

        // then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @Test
    void 로그인_없이_게시물을_단건_조회할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다();
        ArticleIdResponse articleIdResponse = 토론을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 게시물을_조회한다(articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .extracting(
                                ArticleResponse::getTitle,
                                ArticleResponse::getContent,
                                ArticleResponse::getViews,
                                ArticleResponse::isWriter,
                                ArticleResponse::getCreatedAt
                        )
                        .usingRecursiveComparison()
                        .ignoringFields("author")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        new MemberDto("name", "example.com"),
                                        "content",
                                        false,
                                        0,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    //    @Test
//    void 게시글_작성자는_게시물을_수정할_수_있다() {
//        // given
//        TokenResponse tokenResponse = 로그인을_한다();
//        ArticleIdResponse articleIdResponse = 토론을_등록한다(tokenResponse).as(ArticleIdResponse.class);
//
//        // when
//        ExtractableResponse<Response> response = 게시물을_수정한다(tokenResponse, articleIdResponse);
//        ArticleUpdateResponse articleUpdateResponse = response.as(ArticleUpdateResponse.class);
//
//        // then
//        assertAll(
//                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
//                () -> assertThat(articleUpdateResponse.getId()).isEqualTo(articleIdResponse.getId()),
//                () -> assertThat(articleUpdateResponse.getCategory()).isEqualTo(Category.DISCUSSION)
//        );
//    }

    private TokenResponse 로그인을_한다() {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("code")
                .when()
                .post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);
    }

    private ExtractableResponse<Response> 질문을_등록한다(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 토론을_등록한다(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "discussion"))
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시물을_조회한다(ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .when()
                .param("category", "discussion")
                .get("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시물을_수정한다(TokenResponse tokenResponse, ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleUpdateRequest("title2", "content2"))
                .when()
                .put("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }
}

