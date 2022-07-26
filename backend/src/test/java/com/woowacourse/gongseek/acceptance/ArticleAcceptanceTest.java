package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_등록한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleAllResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlesResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:dummy.sql")
public class ArticleAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_게시글을_등록할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);

        // when
        ExtractableResponse<Response> response = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하지_않고_게시글을_등록할_수_없다() {
        // when
        ExtractableResponse<Response> response = 게시물을_등록한다(new TokenResponse(""), Category.QUESTION.getValue());

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 로그인_없이_게시물을_단건_조회할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

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
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

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
    void 게시물을_단건_조회를_계속_하면_조회수가_계속_증가한다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse).as(ArticleIdResponse.class);

        // when
        로그인_후_게시물을_조회한다(tokenResponse, articleIdResponse);
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
                                        2,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 로그인_하지_않으면_게시물을_수정할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_안한_유저가_게시물을_수정한다(articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 게시물_작성자가_아니면_게시물을_수정할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_수정한다(new TokenResponse("abc"), articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 게시물_작성자는_게시물을_수정할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_수정한다(tokenResponse, articleIdResponse);
        ArticleUpdateResponse articleUpdateResponse = response.as(ArticleUpdateResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleUpdateResponse.getId()).isEqualTo(articleIdResponse.getId()),
                () -> assertThat(articleUpdateResponse.getCategory()).isEqualTo(Category.QUESTION.getValue())
        );
    }

    @Test
    void 게시물_작성자는_게시물을_삭제할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 게시물_작성자가_아니면_게시물을_삭제할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_삭제한다(new TokenResponse("abc"), articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 전체_게시물을_최신순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        for (int i = 0; i < 5; i++) {
            게시물을_등록한다(tokenResponse, Category.DISCUSSION.getValue()).as(ArticleIdResponse.class);
        }
        for (int i = 0; i < 5; i++) {
            게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다("all", "latest", 10L);
        ArticlesResponse articlesResponse = response.as(ArticlesResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.isHasNext()).isFalse(),
                () -> assertThat(articlesResponse.getArticles().get(8).getId()).isEqualTo(1L),
                () -> assertThat(articlesResponse.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .isEqualTo(
                                new ArticleAllResponse(
                                        9L,
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "question",
                                        0,
                                        LocalDateTime.now()
                                )
                        )
        );

    }

    @Test
    void 전체_게시물을_조회순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        for (int i = 0; i < 5; i++) {
            ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.DISCUSSION.getValue()).as(
                    ArticleIdResponse.class);
            로그인_안한_유저가_게시물을_조회한다(articleIdResponse);
            댓글을_등록한다(tokenResponse, articleIdResponse);
        }
        for (int i = 0; i < 10; i++) {
            게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다("all", "views", null);
        ArticlesResponse articlesResponse = response.as(ArticlesResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.isHasNext()).isTrue(),
                () -> assertThat(articlesResponse.getArticles().get(8).getCommentCount()).isEqualTo(0),
                () -> assertThat(articlesResponse.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "id")
                        .isEqualTo(
                                new ArticleAllResponse(
                                        1L,
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "discussion",
                                        1,
                                        LocalDateTime.now()
                                )
                        )
        );

    }

    @Test
    void 질문_게시물을_최신순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        for (int i = 0; i < 20; i++) {
            게시물을_등록한다(tokenResponse, Category.QUESTION.getValue()).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "latest", null);
        ArticlesResponse articlesResponse = response.as(ArticlesResponse.class);

        List<Long> ids = articlesResponse.getArticles().stream()
                .map(ArticleAllResponse::getId)
                .collect(Collectors.toList());
        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.isHasNext()).isTrue(),
                () -> assertThat(ids).isEqualTo(List.of(20L, 19L, 18L, 17L, 16L, 15L, 14L, 13L, 12L, 11L))
        );
    }

    @Test
    void 질문_게시물을_조회순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 2);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 1);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "views", null);
        ArticlesResponse articlesResponse = response.as(ArticlesResponse.class);

        List<Long> ids = articlesResponse.getArticles().stream()
                .map(ArticleAllResponse::getId)
                .limit(5)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.isHasNext()).isTrue(),
                () -> assertThat(ids.containsAll(List.of(1L, 2L, 3L, 4L, 5L))).isTrue()
        );
    }

    @Test
    void 토론_게시물을_최신순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "views", null);
        ArticlesResponse articlesResponse = response.as(ArticlesResponse.class);

        List<Long> ids = articlesResponse.getArticles().stream()
                .map(ArticleAllResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.isHasNext()).isFalse(),
                () -> assertThat(ids.containsAll(List.of(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L))).isTrue()
        );
    }

    @Test
    void 토론_게시물을_조회순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 3);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "views", null);
        ArticlesResponse articlesResponse = response.as(ArticlesResponse.class);

        List<Long> ids = articlesResponse.getArticles().stream()
                .map(ArticleAllResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.isHasNext()).isFalse(),
                () -> assertThat(ids.containsAll(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))).isTrue()
        );
    }

    private void 조회수가_있는_게시물_5개를_생성한다(TokenResponse tokenResponse, int count) {
        for (int i = 0; i < 5; i++) {
            ArticleIdResponse response = 게시물을_등록한다(tokenResponse, Category.QUESTION.getValue())
                    .as(ArticleIdResponse.class);
            for (int j = 0; j < count; j++) {
                로그인_안한_유저가_게시물을_조회한다(response);
            }
        }
    }

    private ExtractableResponse<Response> 게시물_전체를_조회한다(String category, String sort, Long cursorId) {
        return RestAssured
                .given().log().all()
                .when()
                .param("category", category)
                .param("sort", sort)
                .param("cursorId", cursorId)
                .param("size", 10)
                .get("/api/articles")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시물을_등록한다(TokenResponse tokenResponse, String category) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", category))
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

