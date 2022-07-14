package com.woowacourse.gongseek.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private OAuthClient githubOAuthClient;

    @Test
    void 리다이렉트_URL_을_반환한다() {
        OAuthLoginUrlResponse oAuthUrl = authService.getLoginUrl();

        assertThat(oAuthUrl).isNotNull();
    }

    @Test
    void 엑세스토큰을_발급한다() {
        given(githubOAuthClient.getMemberProfile(any()))
                .willReturn(new GithubProfileResponse("1", "giron", "example@xxx"));

        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest("code"));

        assertThat(response).isNotNull();
    }

    @Test
    void 기존_유저의_정보가_바뀌면_로그인을_했을때_업데이트되고_엑세스토큰을_발급한다() {
        Member member = new Member("giron", "gyuchool", "qwesad@qwe");
        memberRepository.save(member);
        given(githubOAuthClient.getMemberProfile(any()))
                .willReturn(new GithubProfileResponse("gyuchool", "giron", "example@xxx"));

        TokenResponse response = authService.generateAccessToken(new OAuthCodeRequest("code"));

        assertAll(
                () -> assertThat(member.getAvatarUrl()).isEqualTo("example@xxx"),
                () -> assertThat(response).isNotNull()
        );

    }
}
