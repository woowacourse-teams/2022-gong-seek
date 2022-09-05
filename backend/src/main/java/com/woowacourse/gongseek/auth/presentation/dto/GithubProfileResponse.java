package com.woowacourse.gongseek.auth.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.member.domain.Member;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GithubProfileResponse {

    @JsonProperty("login")
    private String githubId;

    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    public Member toMember() {
        replaceEmptyNameWithGithubId();
        return new Member(name, githubId, avatarUrl);
    }

    private void replaceEmptyNameWithGithubId() {
        if (Objects.isNull(name)) {
            name = githubId;
        }
    }
}
