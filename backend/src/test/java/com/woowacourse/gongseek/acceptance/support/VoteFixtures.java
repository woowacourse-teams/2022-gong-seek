package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class VoteFixtures {

    public static ExtractableResponse<Response> 투표를_생성한다(AccessTokenResponse tokenResponse, Long articleId,
                                                         VoteCreateRequest voteCreateRequest) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .pathParam("articleId", articleId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(voteCreateRequest)
                .post("/api/articles/{articleId}/votes")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 투표를_조회한다(AccessTokenResponse tokenResponse, Long articleId) {
        return RestAssured
                .given().log().all()
                .pathParam("articleId", articleId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/articles/{articleId}/votes")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 투표를_한다(AccessTokenResponse tokenResponse, Long articleId,
                                                       SelectVoteItemIdRequest request) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .pathParam("articleId", articleId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(request)
                .post("/api/articles/{articleId}/votes/do")
                .then().log().all()
                .extract();
    }
}
