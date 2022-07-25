package com.woowacourse.gongseek.auth.support;

import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;
import java.util.Arrays;

public enum GithubClientFixtures {

    주디("1", "주디", "jurlring", "https://avatars.githubusercontent.com/u/78091011?v=4"),
    슬로("2", "슬로", "hanull", "https://avatars.githubusercontent.com/u/46413629?v=4");

    private final String code;
    private final String name;
    private final String githubId;
    private final String avatarUrl;

    GithubClientFixtures(String code, String name, String githubId, String avatarUrl) {
        this.code = code;
        this.name = name;
        this.githubId = githubId;
        this.avatarUrl = avatarUrl;
    }

    public static GithubProfileResponse getGithubProfile(String code) {
        GithubClientFixtures client = Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("로그인이 실패했습니다."));
        return new GithubProfileResponse(client.githubId, client.name, client.avatarUrl);
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
}
