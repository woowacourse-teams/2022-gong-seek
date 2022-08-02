package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import io.restassured.RestAssured;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleFixtures {

    public static ArticleIdResponse 게시물을_등록한다(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ArticleRequest("title", "content", "question", false))
                .when()
                .post("/api/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ArticleIdResponse.class);
    }
}
