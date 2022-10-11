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
import com.woowacourse.gongseek.support.IntegrationTest;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class AuthServiceTest extends IntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        GithubProfileResponse profileResponse = new GithubProfileResponse(
                주디.getGithubId(), 주디.getName(), 주디.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(주디.getCode())).willReturn(profileResponse);
    }

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
        TokenResponse response = authService.generateToken(new OAuthCodeRequest(주디.getCode()));

        assertThat(response).isNotNull();
    }

    @Test
    void 기존_유저의_정보가_바뀌면_로그인을_했을때_업데이트되고_엑세스토큰을_발급한다() {
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
                기론.getGithubId(), null, 기론.getAvatarUrl());
        given(githubOAuthClient.getMemberProfile(기론.getCode())).willReturn(profileResponse);

        authService.generateToken(new OAuthCodeRequest(기론.getCode()));

        Member actual = memberRepository.findByGithubId(기론.getGithubId()).get();
        assertThat(actual.getName()).isEqualTo(기론.getGithubId());
    }

    @Test
    void 리프레시토큰이_유효하면_토큰을_재발급한다() {
        TokenResponse tokenResponse = authService.generateToken(new OAuthCodeRequest(주디.getCode()));

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
    void 리프레시토큰의_발급여부를_true로_업데이트한다() {
        TokenResponse tokenResponse = authService.generateToken(new OAuthCodeRequest(주디.getCode()));

        RefreshToken originRefreshToken = refreshTokenRepository.findById(tokenResponse.getRefreshToken()).get();
        authService.updateRefreshToken(originRefreshToken.getId());
        RefreshToken updatedRefreshToken = refreshTokenRepository.findById(originRefreshToken.getId()).get();

        assertThat(originRefreshToken.isIssue()).isFalse();
        assertThat(updatedRefreshToken.isIssue()).isTrue();
    }
}
