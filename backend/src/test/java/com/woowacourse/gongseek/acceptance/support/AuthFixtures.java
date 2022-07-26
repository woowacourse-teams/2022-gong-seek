package com.woowacourse.gongseek.acceptance.support;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import io.restassured.RestAssured;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("NonAsciiCharacters")
public class AuthFixtures {

    private static final String GITHUB_OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_PROFILE_URL = "https://api.github.com/user";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TokenResponse 로그인을_한다(GithubClientFixtures client, RestTemplate restTemplate) {
        //mockGithubServer(client, restTemplate);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new OAuthCodeRequest(client.getCode()))
                .when()
                .post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class);
    }

    public static OAuthLoginUrlResponse 로그인_URL을_얻는다() {
        return RestAssured
                .given().log().all()
                .when()
                .get("/api/auth/github")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(OAuthLoginUrlResponse.class);
    }

    private static void mockGithubServer(GithubClientFixtures client, RestTemplate restTemplate) {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        getGithubAccessToken(mockServer);
        getGithubProfile(client, mockServer);
    }

    private static void getGithubAccessToken(MockRestServiceServer mockServer) {
        try {
            mockServer.expect(requestTo(GITHUB_OAUTH_ACCESS_TOKEN_URL))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(new TokenResponse("accessToken")),
                            MediaType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException("로그인이 실패했습니다.");
        }
    }

    private static void getGithubProfile(GithubClientFixtures client, MockRestServiceServer mockServer) {
        try {
            mockServer.expect(requestTo(GITHUB_PROFILE_URL))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(
                                    new GithubProfileResponse(
                                            client.getGithubId(), client.getName(), client.getAvatarUrl())),
                            MediaType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException("로그인이 실패했습니다.");
        }
    }
}
