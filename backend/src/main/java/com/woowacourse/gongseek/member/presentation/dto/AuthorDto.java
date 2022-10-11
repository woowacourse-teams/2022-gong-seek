package com.woowacourse.gongseek.member.presentation.dto;

import com.woowacourse.gongseek.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AuthorDto {

    private static final String ANONYMOUS_NAME = "익명";
    private static final String ANONYMOUS_AVATAR_URL = "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png";

    private String name;
    private String avatarUrl;

    public AuthorDto(Member member) {
        this(member.getName(), member.getAvatarUrl());
    }

    public static AuthorDto of(String memberName, String memberAvatarUrl, boolean isAnonymous) {
        if (isAnonymous) {
            return new AuthorDto(ANONYMOUS_NAME, ANONYMOUS_AVATAR_URL);
        }
        return new AuthorDto(memberName, memberAvatarUrl);
    }
}
