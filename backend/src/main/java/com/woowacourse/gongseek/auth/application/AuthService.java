package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.auth.domain.repository.RefreshTokenRepository;
import com.woowacourse.gongseek.auth.exception.InvalidRefreshTokenException;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
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

    //uuid 사용 이유, jwt는 길어서 대역폭이 크다. jwt에는 유저 id의 정보가 들어있다. 혹시나 하는 마음에 아예 일반 문자열인 UUID - 리프레시는 엑세스 토큰을 위한 토큰이므로 정보따위 필요 없다.
    public TokenResponse renewToken(UUID requestToken) {
        RefreshToken refreshToken = refreshTokenRepository.findById(requestToken)
                .orElseThrow(InvalidRefreshTokenException::new);
        //이미 발급되었으면 탈취당함.
        if (refreshToken.isIssue() || refreshToken.isExpired()) {
            refreshTokenRepository.deleteAllByMemberId(refreshToken.getMemberId());
            System.out.println("refreshToken = " + refreshToken);
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
