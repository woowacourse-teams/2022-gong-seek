package com.woowacourse.gongseek.auth.application;

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
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
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

    @Autowired
    private ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    @Test
    void 리다이렉트_URL_을_반환한다() {
        OAuthLoginUrlResponse oAuthUrl = authService.getLoginUrl();

        assertThat(oAuthUrl).isNotNull();
    }

    @Test
    void 엑세스토큰을_발급한다() {
        GithubClientFixtures 주디 = GithubClientFixtures.주디;
        mockGithubServer(주디);

        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));
        mockServer.verify();

        assertThat(response).isNotNull();
    }

    @Test
    void 기존_유저의_정보가_바뀌면_로그인을_했을때_업데이트되고_엑세스토큰을_발급한다() {
        GithubClientFixtures 주디 = GithubClientFixtures.주디;
        Member member = new Member(주디.getGithubId(), 주디.getName(), "previous avatar url");
        memberRepository.save(member);

        mockGithubServer(주디);
        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));
        mockServer.verify();

        Member actual = memberRepository.findByGithubId(주디.getGithubId()).get();
        assertAll(
                () -> assertThat(actual.getAvatarUrl()).isEqualTo(주디.getAvatarUrl()),
                () -> assertThat(response).isNotNull()
        );
    }

    private void mockGithubServer(GithubClientFixtures client) {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        try {
            mockServer.expect(requestTo("https://github.com/login/oauth/access_token")).andRespond(
                    withSuccess(objectMapper.writeValueAsString(new TokenResponse("accessToken")),
                            MediaType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException("로그인이 실패했습니다.");
        }

        try {
            mockServer.expect(requestTo("https://api.github.com/user")).andExpect(method(HttpMethod.GET)).andRespond(
                    withSuccess(objectMapper.writeValueAsString(
                                    new GithubProfileResponse(client.getGithubId(), client.getName(), client.getAvatarUrl())),
                            MediaType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException("로그인이 실패했습니다.");
        }
    }
}
