package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.infra.GithubOAuthClient;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
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

        return memberRepository.findByGithubId(member.getGithubId())
                .map(foundMember -> updateMember(foundMember, member))
                .orElseGet(() -> saveMember(member));
    }

    private TokenResponse updateMember(Member foundMember, Member newMember) {
        foundMember.updateAvatarUrl(newMember.getAvatarUrl());
        return getTokenResponse(foundMember);
    }

    private TokenResponse saveMember(Member newMember) {
        memberRepository.save(newMember);
        return getTokenResponse(newMember);
    }

    private TokenResponse getTokenResponse(Member member) {
        String accessToken = jwtTokenProvider.createToken(String.valueOf(member.getId()));
        return new TokenResponse(accessToken);
    }
}
