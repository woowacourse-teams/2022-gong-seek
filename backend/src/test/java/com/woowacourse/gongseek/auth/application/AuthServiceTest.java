package com.woowacourse.gongseek.auth.application;

import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 리다이렉트_URL_을_반환한다() {
        OAuthLoginUrlResponse oAuthUrl = authService.getLoginUrl();

        assertThat(oAuthUrl).isNotNull();
    }

    @Test
    void 엑세스토큰을_발급한다() throws JsonProcessingException, URISyntaxException {
        mockGithubServer(주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));
        mockServer.verify();

        assertThat(response).isNotNull();
    }

    @Test
    void 기존_유저의_정보가_바뀌면_로그인을_했을때_업데이트되고_엑세스토큰을_발급한다() throws URISyntaxException, JsonProcessingException {
        mockGithubServer(주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));
        mockServer.verify();

        mockGithubServer(주디.getGithubId(), 주디.getName(), "update email");
        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));
        mockServer.verify();

        assertAll(
                () -> assertThat(memberRepository.findByGithubId(주디.getGithubId()).get().getAvatarUrl()).isEqualTo(
                        "update email"),
                () -> assertThat(response).isNotNull()
        );
    }

    private void mockGithubServer(String githubId, String name, String avatarUrl)
            throws JsonProcessingException, URISyntaxException {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("https://github.com/login/oauth/access_token"))
                .andRespond(withSuccess(
                        objectMapper.writeValueAsString(new TokenResponse("accessToken")),
                        MediaType.APPLICATION_JSON));

        mockServer.expect(requestTo(new URI("https://api.github.com/user")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        objectMapper.writeValueAsString(new GithubProfileResponse(githubId, name, avatarUrl)),
                        MediaType.APPLICATION_JSON));
    }
}
