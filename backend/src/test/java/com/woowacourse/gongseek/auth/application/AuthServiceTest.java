package com.woowacourse.gongseek.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import com.woowacourse.gongseek.common.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private OAuthClient githubOAuthClient;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

    @Test
    void 리다이렉트_URL_을_반환한다() {
        OAuthLoginUrlResponse oAuthUrl = authService.getLoginUrl();

        assertThat(oAuthUrl).isNotNull();
    }

    @Test
    void 엑세스토큰을_발급한다() {
        GithubClientFixtures 주디 = GithubClientFixtures.주디;
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(주디.getCode())).willReturn(profileResponse);

        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));

        assertThat(response).isNotNull();
    }

    @Test
    void 기존_유저의_정보가_바뀌면_로그인을_했을때_업데이트되고_엑세스토큰을_발급한다() {
        GithubClientFixtures 주디 = GithubClientFixtures.주디;
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(주디.getCode())).willReturn(profileResponse);
        Member member = new Member(주디.getGithubId(), 주디.getName(), "previous avatar url");
        memberRepository.save(member);

        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest(주디.getCode()));

        Member actual = memberRepository.findByGithubId(주디.getGithubId()).get();
        assertAll(
                () -> assertThat(actual.getAvatarUrl()).isEqualTo(주디.getAvatarUrl()),
                () -> assertThat(response).isNotNull()
        );
    }
}
