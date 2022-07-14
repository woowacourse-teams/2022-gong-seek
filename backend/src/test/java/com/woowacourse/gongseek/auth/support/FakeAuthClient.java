package com.woowacourse.gongseek.auth.support;

import com.woowacourse.gongseek.auth.application.OAuthClient;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;

public class FakeAuthClient implements OAuthClient {

    private static final String BASE_URL = "https://github.com";
    private static final String LOGIN_URL_SUFFIX = "/login/oauth/authorize?client_id=%s&redirect_uri=%s";
    private static final String clientId = "clientId";
    private static final String REDIRECT_URL = "http://localhost:8080/callback";

    @Override
    public String getRedirectUrl() {
        return String.format(BASE_URL + LOGIN_URL_SUFFIX, clientId, REDIRECT_URL);
    }

    @Override
    public GithubProfileResponse getMemberProfile(String code) {
        return GithubClientFixtures.getGithubProfile(code);
    }
}
