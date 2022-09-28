package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.auth.domain.repository.RefreshTokenRepository;
import com.woowacourse.gongseek.auth.exception.InvalidRefreshTokenException;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.Objects;
import java.util.UUID;
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
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.create(member.getId()));
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getId())
                .build();
    }

    public TokenResponse renewToken(UUID requestToken) {
        if (Objects.isNull(requestToken)) {
            throw new InvalidRefreshTokenException();
        }
        RefreshToken refreshToken = refreshTokenRepository.findById(requestToken)
                .orElseThrow(InvalidRefreshTokenException::new);
        if (refreshToken.isIssue() || refreshToken.isExpired()) {
            refreshTokenRepository.deleteAllByMemberId(refreshToken.getMemberId());
            throw new InvalidRefreshTokenException();
        }
        refreshToken.used();

        RefreshToken newRefreshToken = refreshTokenRepository.save(RefreshToken.create(refreshToken.getMemberId()));
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(refreshToken.getMemberId()));
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getId())
                .build();
    }
}
