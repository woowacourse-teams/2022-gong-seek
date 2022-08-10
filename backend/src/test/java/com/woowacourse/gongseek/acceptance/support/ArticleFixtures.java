package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleFixtures {

    public static ExtractableResponse<Response> 특정_게시물을_등록한다(AccessTokenResponse tokenResponse,
                                                             ArticleRequest articleRequest) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(articleRequest)
                .when()
                .post("/api/articles")
                .then().log().all()
                .extract();
    }

    public static ArticleIdResponse 게시물을_등록한다(AccessTokenResponse tokenResponse) {
        ArticleRequest request = new ArticleRequest("title", "content", "question", false);
        return 특정_게시물을_등록한다(tokenResponse, request).as(ArticleIdResponse.class);
    }

    public static ExtractableResponse<Response> 기명으로_게시물을_등록한다(AccessTokenResponse tokenResponse, Category category) {
        ArticleRequest request = new ArticleRequest("title", "content", category.getValue(), false);
        return 특정_게시물을_등록한다(tokenResponse, request);
    }

    public static ExtractableResponse<Response> 익명으로_게시물을_등록한다(AccessTokenResponse tokenResponse, Category category) {
        ArticleRequest request = new ArticleRequest("title", "content", category.getValue(), true);
        return 특정_게시물을_등록한다(tokenResponse, request);
    }

    public static void 조회수가_있는_게시물_5개를_생성한다(AccessTokenResponse tokenResponse, int count, Category category) {
        for (int i = 0; i < 5; i++) {
            ArticleIdResponse response = 기명으로_게시물을_등록한다(tokenResponse, category)
                    .as(ArticleIdResponse.class);
            for (int j = 0; j < count; j++) {
                로그인을_하지_않고_게시물을_조회한다(response);
            }
        }
    }

    public static ExtractableResponse<Response> 로그인_후_게시물을_조회한다(AccessTokenResponse tokenResponse,
                                                                ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인을_하지_않고_게시물을_조회한다(ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .when()
                .get("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 게시물_전체를_조회한다(String category, String sort, Long cursorId,
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

    public static ExtractableResponse<Response> 로그인_후_게시물을_수정한다(AccessTokenResponse tokenResponse,
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

    public static ExtractableResponse<Response> 로그인을_하지_않고_게시물을_수정한다(ArticleIdResponse articleIdResponse) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleUpdateRequest("title2", "content2"))
                .when()
                .put("/api/articles/{articleId}", articleIdResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인_후_게시물을_삭제한다(AccessTokenResponse tokenResponse,
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

    public static ArticlePageResponse 게시물을_처음_검색한다(int pageSize, String searchText) {
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

    public static ArticlePageResponse 게시물을_검색한다(long cursorId, int pageSize, String searchText) {
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
}
