package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixture.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.TempArticleFixture.임시_게시물을_등록한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.TempArticlesResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TempArticleAcceptanceTest extends AcceptanceTest {

    @Test
    void 슬로가_로그인을_하고_게시글_임시서장을_할_수_있다() {
        final AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        final ArticleRequest request = new ArticleRequest("title", "content", Category.DISCUSSION.getValue(),
                List.of("Spring"), false);

        final ExtractableResponse<Response> response = 임시_게시물을_등록한다(tokenResponse, request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        /**
         * todo
         * 임시 게시물 id값을 확인한다.
         */
    }

    @Test
    void 슬로가_로그인을_하고_전체_임시_게시글을_조회할_수_있다() {
        final AccessTokenResponse tokenResponse = 로그인을_한다(슬로);
        임시_게시물을_등록한다(tokenResponse,
                new ArticleRequest("title", "content", Category.DISCUSSION.getValue(), List.of("Spring"), false));
        임시_게시물을_등록한다(tokenResponse,
                new ArticleRequest("title2", "content2", Category.QUESTION.getValue(), List.of("Spring2"), false));

        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/temp-articles")
                .then().log().all()
                .extract();
        final TempArticlesResponse tempArticlesResponse = response.as(TempArticlesResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(tempArticlesResponse.getValues()).hasSize(2),
                () -> assertThat(tempArticlesResponse.getValues().get(0).getTitle()).isEqualTo("title"),
                () -> assertThat(tempArticlesResponse.getValues().get(1).getTitle()).isEqualTo("title2")
        );
    }
}
