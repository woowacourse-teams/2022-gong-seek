package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_등록한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
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
import java.util.List;
import java.util.stream.Collectors;
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
        ExtractableResponse<Response> response = 게시물을_등록한다(tokenResponse, Category.QUESTION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하지_않고_게시글을_등록할_수_없다() {
        // when
        ExtractableResponse<Response> response = 게시물을_등록한다(new TokenResponse(""), Category.QUESTION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 로그인_없이_게시물을_단건_조회할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

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
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

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
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

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
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_안한_유저가_게시물을_수정한다(articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 게시물_작성자가_아니면_게시물을_수정할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_수정한다(new TokenResponse("abc"), articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 게시물_작성자는_게시물을_수정할_수_있다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

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
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 게시물_작성자가_아니면_게시물을_삭제할_수_없다() {
        // given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시물을_삭제한다(new TokenResponse("abc"), articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 전체_게시물을_최신순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0, Category.DISCUSSION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 2, Category.DISCUSSION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 3, Category.QUESTION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 1, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다("all", "latest", null, null);
        ArticlePageResponse firstResponse = response.as(ArticlePageResponse.class);
        List<ArticlePreviewResponse> firstArticles = firstResponse.getArticles();

        ExtractableResponse<Response> secondResponse = 게시물_전체를_조회한다("all", "latest",
                firstArticles.get(firstArticles.size() - 1).getId(), null);
        ArticlePageResponse secondArticles = secondResponse.as(ArticlePageResponse.class);
        //then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(secondArticles.isHasNext()).isFalse(),
                () -> assertThat(secondArticles.getArticles().get(9).getId()).isEqualTo(1L),
                () -> assertThat(secondArticles.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .isEqualTo(
                                new ArticlePreviewResponse(
                                        10L,
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "discussion",
                                        0,
                                        2,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 전체_게시물을_조회순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 5, Category.DISCUSSION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0, Category.QUESTION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 3, Category.DISCUSSION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 1, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다("all", "views", null, null);
        ArticlePageResponse firstResponse = response.as(ArticlePageResponse.class);
        List<ArticlePreviewResponse> firstArticles = firstResponse.getArticles();

        ArticlePreviewResponse lastArticle = firstArticles.get(firstArticles.size() - 1);
        ExtractableResponse<Response> secondResponse = 게시물_전체를_조회한다("all", "views",
                lastArticle.getId(), lastArticle.getViews());
        ArticlePageResponse secondArticles = secondResponse.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(secondArticles.isHasNext()).isFalse(),
                () -> assertThat(secondArticles.getArticles().get(9).getViews()).isEqualTo(0),
                () -> assertThat(secondArticles.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "id")
                        .isEqualTo(
                                new ArticlePreviewResponse(
                                        16L,
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "question",
                                        0,
                                        1,
                                        LocalDateTime.now()
                                )
                        )
        );

    }

    @Test
    void 전체_게시물을_조회하면_댓글_개수도_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        for (int i = 0; i < 5; i++) {
            ArticleIdResponse articleIdResponse = 게시물을_등록한다(tokenResponse, Category.DISCUSSION).as(
                    ArticleIdResponse.class);
            로그인_안한_유저가_게시물을_조회한다(articleIdResponse);
            댓글을_등록한다(tokenResponse, articleIdResponse);
        }
        for (int i = 0; i < 10; i++) {
            게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다("all", "views", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.isHasNext()).isTrue(),
                () -> assertThat(articlePageResponse.getArticles().get(8).getCommentCount()).isEqualTo(0),
                () -> assertThat(articlePageResponse.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "id")
                        .isEqualTo(
                                new ArticlePreviewResponse(
                                        1L,
                                        "title",
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "discussion",
                                        1,
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
            게시물을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "latest", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());
        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.isHasNext()).isTrue(),
                () -> assertThat(ids).isEqualTo(List.of(20L, 19L, 18L, 17L, 16L, 15L, 14L, 13L, 12L, 11L))
        );
    }

    @Test
    void 질문_게시물을_조회순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 1, Category.QUESTION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 2, Category.QUESTION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "views", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .limit(5)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.isHasNext()).isTrue(),
                () -> assertThat(ids.containsAll(List.of(6L, 7L, 8L, 9L, 10L))).isTrue()
        );
    }

    @Test
    void 토론_게시물을_최신순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0, Category.QUESTION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.QUESTION.getValue(), "views", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.isHasNext()).isFalse(),
                () -> assertThat(ids.containsAll(List.of(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L))).isTrue()
        );
    }

    @Test
    void 토론_게시물을_조회순으로_조회한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 3, Category.DISCUSSION);
        조회수가_있는_게시물_5개를_생성한다(tokenResponse, 0, Category.DISCUSSION);

        //when
        ExtractableResponse<Response> response = 게시물_전체를_조회한다(Category.DISCUSSION.getValue(), "views",
                null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.isHasNext()).isFalse(),
                () -> assertThat(ids.containsAll(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))).isTrue()
        );
    }

    @Test
    void 게시물을_검색한다() {
        //given
        TokenResponse tokenResponse = 로그인을_한다(주디);
        특정_게시물을_등록한다(new ArticleRequest("커스텀 예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("커스텀예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("예외를 커스텀하려면?", "내용", Category.QUESTION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("예외를커스텀하려면?", "내용", Category.QUESTION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("제목", "예외 어떻게 커스텀하죠 ㅠㅠ", Category.QUESTION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("제목", "예외 어떻게커스텀하죠 ㅠㅠ", Category.QUESTION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("제목", "예외는 이렇게 커스텀 하면 됩니다.", Category.DISCUSSION.getValue()));
        특정_게시물을_등록한다(new ArticleRequest("제목", "예외는 이렇게 커스텀하면 됩니다.", Category.DISCUSSION.getValue()));

        //when
        int pageSize = 4;
        String searchText = "커스텀";
        ArticlePageResponse firstPage = 게시물을_처음_검색한다(pageSize, searchText);
        ArticlePageResponse secondPage = 게시물을_검색한다(firstPage.getArticles().get(pageSize - 1).getId(), pageSize,
                searchText);
        long 첫번째_페이지에서_title이_제목인_게시물의_수 = firstPage.getArticles()
                .stream()
                .filter(articlePreviewResponse -> articlePreviewResponse.getTitle().equals("제목"))
                .count();
        long 두번째_페이지에서_content가_내용인_게시물의_수 = secondPage.getArticles()
                .stream()
                .filter(articlePreviewResponse -> articlePreviewResponse.getContent().equals("내용"))
                .count();

        //then
        assertAll(
                () -> assertThat(firstPage.isHasNext()).isTrue(),
                () -> assertThat(firstPage.getArticles()).hasSize(4),
                () -> assertThat(secondPage.isHasNext()).isFalse(),
                () -> assertThat(secondPage.getArticles()).hasSize(4),
                () -> assertThat(첫번째_페이지에서_title이_제목인_게시물의_수).isEqualTo(4),
                () -> assertThat(두번째_페이지에서_content가_내용인_게시물의_수).isEqualTo(4)
        );
    }

    private ArticlePageResponse 게시물을_처음_검색한다(int pageSize, String searchText) {
        return RestAssured
                .given().log().all()
                .when()
                .param("pageSize", pageSize)
                .param("searchText", searchText)
                .get("/api/articles/search")
                .then().log().all()
                .extract()
                .as(ArticlePageResponse.class);
    }

    private ArticlePageResponse 게시물을_검색한다(long cursorId, int pageSize, String searchText) {
        return RestAssured
                .given().log().all()
                .when()
                .param("cursorId", cursorId)
                .param("pageSize", pageSize)
                .param("searchText", searchText)
                .get("/api/articles/search")
                .then().log().all()
                .extract()
                .as(ArticlePageResponse.class);
    }

    private void 특정_게시물을_등록한다(ArticleRequest articleRequest) {
        TokenResponse tokenResponse = 로그인을_한다(주디);
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(articleRequest)
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();
    }

    private void 조회수가_있는_게시물_5개를_생성한다(TokenResponse tokenResponse, int count, Category category) {
        for (int i = 0; i < 5; i++) {
            ArticleIdResponse response = 게시물을_등록한다(tokenResponse, category)
                    .as(ArticleIdResponse.class);
            for (int j = 0; j < count; j++) {
                로그인_안한_유저가_게시물을_조회한다(response);
            }
        }
    }

    private ExtractableResponse<Response> 게시물_전체를_조회한다(String category, String sort, Long cursorId,
                                                       Integer cursorViews) {
        return RestAssured
                .given().log().all()
                .when()
                .param("category", category)
                .param("sort", sort)
                .param("cursorId", cursorId)
                .param("cursorViews", cursorViews)
                .param("pageSize", 10)
                .get("/api/articles")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시물을_등록한다(TokenResponse tokenResponse, Category category) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", category.getValue()))
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
