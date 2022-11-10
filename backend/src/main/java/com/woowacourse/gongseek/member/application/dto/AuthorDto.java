package com.woowacourse.gongseek.member.application.dto;

import com.woowacourse.gongseek.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AuthorDto {

    private String name;
    private String avatarUrl;

    public AuthorDto(Member member) {
        this(member.getName(), member.getAvatarUrl());
    }
}
