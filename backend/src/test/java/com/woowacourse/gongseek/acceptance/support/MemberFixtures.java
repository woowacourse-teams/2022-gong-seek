package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class MemberFixtures {

    public static ExtractableResponse<Response> 내_정보를_조회한다(AccessTokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내가_작성한_게시글들을_조회한다(AccessTokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/members/me/articles")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내가_작성한_댓글들을_조회한다(AccessTokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .when()
                .get("/api/members/me/comments")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이름을_수정한다(AccessTokenResponse tokenResponse, String name) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MemberUpdateRequest(name))
                .when()
                .patch("/api/members/me")
                .then().log().all()
                .extract();
    }
}
