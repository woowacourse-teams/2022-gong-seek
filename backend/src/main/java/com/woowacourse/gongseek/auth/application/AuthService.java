package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.domain.GithubOAuthClient;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final GithubOAuthClient githubOAuthClient;

    public OAuthLoginUrlResponse getLoginUrl() {
        return new OAuthLoginUrlResponse(githubOAuthClient.getRedirectUrl());
    }
}
