package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final GithubOAuthClient githubOAuthClient;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuthLoginUrlResponse getLoginUrl() {
        return new OAuthLoginUrlResponse(githubOAuthClient.getRedirectUrl());
    }

    public TokenResponse generateAccessToken(OAuthCodeRequest OAuthCodeRequest) {
        Member member = githubOAuthClient.getMemberProfile(OAuthCodeRequest.getCode()).toMember();

        memberRepository.findByGithubId(member.getGithubId())
                .ifPresentOrElse(
                        it -> it.updateAvatarUrl(member.getAvatarUrl()),
                        () -> memberRepository.save(member)
                );

        String accessToken = jwtTokenProvider.createToken(String.valueOf(member.getId()));
        return new TokenResponse(accessToken);
    }
}
