package com.woowacourse.gongseek.auth.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.presentation.dto.GithubAccessTokenRequest;
import com.woowacourse.gongseek.auth.presentation.dto.GithubAccessTokenResponse;
import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Getter
@Component
public class GithubOAuthClient {

    private static final String BASE_URL = "https://github.com";
    private static final String REDIRECT_URL = "http://localhost:8080/callback";
    private static final String PROFILE_URL = "https://api.github.com/user";
    private static final String LOGIN_URL_SUFFIX = "/login/oauth/authorize?client_id=%s&redirect_uri=%s";
    private static final String GITHUB_ACCESS_URL_SUFFIX = "/login/oauth/access_token";
    private static final String TOKEN = "token ";

    private final String clientId;
    private final String clientSecret;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public GithubOAuthClient(
            @Value("${security.oauth2.client-id}") String clientId,
            @Value("${security.oauth2.client-secret}") String clientSecret,
            ObjectMapper objectMapper,
            RestTemplate restTemplate
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public String getRedirectUrl() {
        return String.format(BASE_URL + LOGIN_URL_SUFFIX, clientId, REDIRECT_URL);
    }

    public GithubProfileResponse getMemberProfile(String code) {
        GithubAccessTokenResponse accessTokenResponse = getGithubAccessToken(code);
        return getGithubProfile(accessTokenResponse);
    }

    private GithubAccessTokenResponse getGithubAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        GithubAccessTokenRequest accessTokenRequest = new GithubAccessTokenRequest(clientId, clientSecret, code);

        HttpEntity<GithubAccessTokenRequest> entity = new HttpEntity<>(accessTokenRequest, headers);
        GithubAccessTokenResponse accessTokenResponse = restTemplate.exchange(
                BASE_URL + GITHUB_ACCESS_URL_SUFFIX,
                HttpMethod.POST,
                entity,
                GithubAccessTokenResponse.class
        ).getBody();

        validateToken(accessTokenResponse);
        return accessTokenResponse;
    }

    private void validateToken(GithubAccessTokenResponse accessTokenResponse) {
        if (Objects.isNull(accessTokenResponse)) {
            throw new IllegalStateException("로그인이 실패했습니다.");
        }
    }

    private GithubProfileResponse getGithubProfile(GithubAccessTokenResponse accessTokenResponse) {
        String accessToken = accessTokenResponse.getAccessToken();
        String token = TOKEN + accessToken;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
        GithubProfileResponse profileResponse = restTemplate.exchange(
                PROFILE_URL,
                HttpMethod.GET,
                httpEntity,
                GithubProfileResponse.class,
                objectMapper
        ).getBody();

        validateProfile(profileResponse);
        return profileResponse;
    }

    private void validateProfile(GithubProfileResponse profileResponse) {
        if (Objects.isNull(profileResponse)) {
            throw new IllegalStateException("로그인이 실패했습니다.");
        }
    }
}
