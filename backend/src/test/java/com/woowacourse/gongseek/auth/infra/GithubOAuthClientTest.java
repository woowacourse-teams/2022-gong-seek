package com.woowacourse.gongseek.auth.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.application.OAuthClient;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class GithubOAuthClientTest {

    @Autowired
    private OAuthClient oAuthClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    @Test
    void 깃허브_프로필을_조회한다() {
        GithubClientFixtures 주디 = GithubClientFixtures.주디;
        mockGithubServer(주디);
        GithubProfileResponse profile = oAuthClient.getMemberProfile(주디.getCode());
        mockServer.verify();
        assertAll(
                () -> assertThat(profile.getGithubId()).isEqualTo(주디.getGithubId()),
                () -> assertThat(profile.getName()).isEqualTo(주디.getName()),
                () -> assertThat(profile.getAvatarUrl()).isEqualTo(주디.getAvatarUrl())
        );
    }

    private void mockGithubServer(GithubClientFixtures client) {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        try {
            mockServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(new TokenResponse("accessToken")),
                            MediaType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            mockServer.expect(requestTo("https://api.github.com/user"))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(new GithubProfileResponse(
                                    client.getGithubId(),
                                    client.getName(),
                                    client.getAvatarUrl()
                            )),
                            MediaType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
