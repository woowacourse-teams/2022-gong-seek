package com.woowacourse.gongseek.auth.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GithubOAuthClient {

    private static final String BASE_URL = "https://github.com";
    private static final String LOGIN_URL_SUFFIX =
            "/login/oauth/authorize?client_id=%s&redirect_uri=%s";
    private static final String REDIRECT_URL = "http://localhost:8080/callback";

    private final String clientId;
    private final String clientSecret;

    public GithubOAuthClient(
            @Value("${security.oauth2.client-id}") String clientId,
            @Value("${security.oauth2.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getRedirectUrl() {
        return String.format(BASE_URL + LOGIN_URL_SUFFIX, clientId, REDIRECT_URL);
    }
}
