package com.woowacourse.gongseek.member.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Name name;

    @Column(nullable = false)
    private String githubId;

    @Column(nullable = false)
    private String avatarUrl;

    public Member(String name, String githubId, String avatarUrl) {
        this.name = new Name(name);
        this.githubId = githubId;
        this.avatarUrl = avatarUrl;
    }

    public boolean isAnonymous(String cipherId) {
        return githubId.equals(cipherId);
    }

    public void updateAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void updateName(String value) {
        this.name = new Name(value);
    }

    public String getName(){
        return name.getValue();
    }
}
