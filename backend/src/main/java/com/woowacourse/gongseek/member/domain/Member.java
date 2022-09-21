package com.woowacourse.gongseek.member.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    private static final Long ANONYMOUS_ID = 0L;
    private static final String ANONYMOUS_NAME = "익명";
    private static final String ANONYMOUS_GITHUB_ID = "githubId";
    private static final String ANONYMOUS_AVATAR_URL = "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(unique = true, nullable = false)
    private Name name;

    @Column(nullable = false)
    private String githubId;

    @Column(nullable = false)
    private String avatarUrl;

    public Member(String name, String githubId, String avatarUrl) {
        name = replaceEmptyNameWithGithubId(name, githubId);
        this.name = new Name(name);
        this.githubId = githubId;
        this.avatarUrl = avatarUrl;
    }

    private String replaceEmptyNameWithGithubId(String name, String githubId) {
        if (Objects.isNull(name)) {
            name = githubId;
        }
        return name;
    }

    public void updateAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void updateName(String value) {
        this.name = new Name(value);
    }

    public Member getMemberOrAnonymous(boolean isAnonymous) {
        if (isAnonymous) {
            return new Member(this.getId(), new Name(ANONYMOUS_NAME), ANONYMOUS_GITHUB_ID, ANONYMOUS_AVATAR_URL);
        }
        return this;
    }

    public String getName() {
        return name.getValue();
    }
}
