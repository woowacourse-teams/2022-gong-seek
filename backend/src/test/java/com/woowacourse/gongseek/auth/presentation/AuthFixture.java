package com.woowacourse.gongseek.auth.presentation;

import java.util.Arrays;

public enum AuthFixture {

    주디("1", "주디", "jurlring", "https://avatars.githubusercontent.com/u/78091011?v=4", "accessToken1"),
    슬로("2", "슬로", "hanull", "https://avatars.githubusercontent.com/u/46413629?v=4", "accessToken2");

    private final String code;
    private final String name;
    private final String githubId;
    private final String avatarUrl;
    private final String accessToken;

    AuthFixture(String code, String name, String githubId, String avatarUrl, String accessToken) {
        this.code = code;
        this.name = name;
        this.githubId = githubId;
        this.avatarUrl = avatarUrl;
        this.accessToken = accessToken;
    }

    public static String generateAccessToken(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("로그인이 실패했습니다."))
                .getAccessToken();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getGithubId() {
        return githubId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
