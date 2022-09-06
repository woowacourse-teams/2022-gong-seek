package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ArticleTempAcceptanceTest extends AcceptanceTest {

    @Test
    void 슬로가_로그인을_하고_게시글_임시서장을_할_수_있다() {
        final AccessTokenResponse tokenResponse = 로그인을_한다(슬로);

        final ArticleRequest request = new ArticleRequest("title", "content", Category.DISCUSSION.getValue(),
                List.of("Spring"), false);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/temp-articles")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 슬로가_로그인을_하고_전체_임시_게시글을_조회할_수_있다() {
        final AccessTokenResponse tokenResponse = 로그인을_한다(슬로);

        final ArticleRequest request1 = new ArticleRequest("title", "content", Category.DISCUSSION.getValue(),
                List.of("Spring"), false);
        final ArticleRequest request2 = new ArticleRequest("title2", "content2", Category.QUESTION.getValue(),
                List.of("Spring2"), false);


    }
}
