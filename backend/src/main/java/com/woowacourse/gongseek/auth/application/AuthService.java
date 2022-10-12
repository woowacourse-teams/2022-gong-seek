package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.auth.domain.repository.RefreshTokenRepository;
import com.woowacourse.gongseek.auth.exception.InvalidRefreshTokenException;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
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
        GithubProfileResponse githubProfile = githubOAuthClient.getMemberProfile(OAuthCodeRequest.getCode());

        return memberRepository.findByGithubId(githubProfile.getGithubId())
                .map(foundMember -> updateMember(foundMember, githubProfile))
                .orElseGet(() -> saveMember(githubProfile));
    }

    private TokenResponse updateMember(Member foundMember, GithubProfileResponse githubProfile) {
        foundMember.updateAvatarUrl(githubProfile.getAvatarUrl());
        return getTokenResponse(foundMember);
    }

    private TokenResponse saveMember(GithubProfileResponse githubProfile) {
        return getTokenResponse(memberRepository.save(githubProfile.toMember()));
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
        RefreshToken refreshToken = refreshTokenRepository.findById(requestToken)
                .orElseThrow(InvalidRefreshTokenException::new);
        if (refreshToken.isIssue() || refreshToken.isExpired()) {
            List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByMemberId(refreshToken.getMemberId());
            refreshTokenRepository.deleteAll(refreshTokens);
            throw new InvalidRefreshTokenException();
        }
        updateIssue(refreshToken);

        RefreshToken newRefreshToken = refreshTokenRepository.save(RefreshToken.create(refreshToken.getMemberId()));
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(refreshToken.getMemberId()));
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getId())
                .build();
    }

    public void updateRefreshToken(UUID value) {
        RefreshToken refreshToken = refreshTokenRepository.findById(value)
                .orElseThrow(InvalidRefreshTokenException::new);
        updateIssue(refreshToken);
    }

    private void updateIssue(RefreshToken refreshToken) {
        refreshToken.used();
        refreshTokenRepository.save(refreshToken);
    }
}
