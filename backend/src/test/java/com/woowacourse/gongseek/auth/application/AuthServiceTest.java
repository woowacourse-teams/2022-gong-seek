package com.woowacourse.gongseek.auth.application;

import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.기론;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.auth.domain.repository.RefreshTokenRepository;
import com.woowacourse.gongseek.auth.exception.InvalidRefreshTokenException;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.DatabaseCleaner;
import java.util.Iterator;
import java.util.UUID;
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
    private RefreshTokenRepository refreshTokenRepository;

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
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(주디.getCode())).willReturn(profileResponse);

        TokenResponse response = authService.generateToken(new OAuthCodeRequest(주디.getCode()));

        assertThat(response).isNotNull();
    }

    @Test
    void 기존_유저의_정보가_바뀌면_로그인을_했을때_업데이트되고_엑세스토큰을_발급한다() {
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(주디.getCode())).willReturn(profileResponse);
        Member member = new Member(주디.getGithubId(), 주디.getName(), "previous avatar url");
        memberRepository.save(member);

        TokenResponse response = authService.generateToken(new OAuthCodeRequest(주디.getCode()));

        Member actual = memberRepository.findByGithubId(주디.getGithubId()).get();
        assertAll(
                () -> assertThat(actual.getAvatarUrl()).isEqualTo(주디.getAvatarUrl()),
                () -> assertThat(response).isNotNull()
        );
    }

    @Test
    void 유저의_이름이_null이면_깃허브_아이디로_대체된다() {
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                주디.getGithubId(), null, 주디.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(주디.getCode())).willReturn(profileResponse);

        authService.generateToken(new OAuthCodeRequest(주디.getCode()));

        Member actual = memberRepository.findByGithubId(주디.getGithubId()).get();
        assertThat(actual.getName()).isEqualTo(주디.getGithubId());
    }

    @Test
    void 리프레시토큰이_유효하면_토큰을_재발급한다() {
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                기론.getGithubId(), 기론.getName(), 기론.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(기론.getCode())).willReturn(profileResponse);

        TokenResponse tokenResponse = authService.generateToken(new OAuthCodeRequest(기론.getCode()));

        TokenResponse renewToken = authService.renewToken(tokenResponse.getRefreshToken());
        assertAll(
                () -> assertThat(renewToken.getRefreshToken()).isNotNull(),
                () -> assertThat(renewToken.getAccessToken()).isNotNull()
        );
    }

    @Test
    void 유효하지않는_리프레시토큰이_들어오면_예외를_발생한다() {
        assertThatThrownBy(() -> authService.renewToken(UUID.randomUUID()))
                .isExactlyInstanceOf(InvalidRefreshTokenException.class)
                .hasMessage("리프레시 토큰이 유효하지 않습니다.");
    }

    @Test
    void 유효하지않는_리프레시토큰이_들어오면_예외가_발생한다_그리고_해당_리프레시토큰을_지운다() {

        GithubProfileResponse profileResponse = new GithubProfileResponse(
                기론.getGithubId(), 기론.getName(), 기론.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(기론.getCode())).willReturn(profileResponse);

        TokenResponse tokenResponse = authService.generateToken(new OAuthCodeRequest(기론.getCode()));
        authService.renewToken(tokenResponse.getRefreshToken());

        RefreshToken refreshToken = refreshTokenRepository.findById(tokenResponse.getRefreshToken()).get();

        assertAll(
                () -> assertThat(refreshToken.isIssue()).isTrue(),
                () -> assertThatThrownBy(() -> authService.renewToken(refreshToken.getId()))
                        .isExactlyInstanceOf(InvalidRefreshTokenException.class)
                        .hasMessage("리프레시 토큰이 유효하지 않습니다."),
                () -> assertThat(refreshTokenRepository.findById(tokenResponse.getRefreshToken())).isEmpty()
        );
    }
}
