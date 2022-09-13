package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.auth.domain.repository.RefreshTokenRepository;
import com.woowacourse.gongseek.auth.exception.InvalidRefreshTokenException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final OAuthClient githubOAuthClient;
    private final MemberRepository memberRepository;
    private final TokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public OAuthLoginUrlResponse getLoginUrl() {
        return new OAuthLoginUrlResponse(githubOAuthClient.getRedirectUrl());
    }

    public TokenResponse generateToken(OAuthCodeRequest OAuthCodeRequest) {
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
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.create(member));
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getValue())
                .build();
    }

    public TokenResponse renewToken(AppMember appMember, String refreshToken) {
        Member member = memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new MemberNotFoundException(appMember.getPayload()));

        RefreshToken token = refreshTokenRepository.findByValueAndMemberId(refreshToken, member.getId())
                .orElseThrow(() -> {
                    refreshTokenRepository.deleteByMemberId(member.getId());
                    throw new InvalidRefreshTokenException();
                });
        validateExpiryDate(token);
        refreshTokenRepository.deleteById(token.getId());
        refreshTokenRepository.save(RefreshToken.create(member));

        return getTokenResponse(member);
    }

    private void validateExpiryDate(RefreshToken token) {
        if (token.isExpired()) {
            throw new InvalidRefreshTokenException();
        }
    }
}
