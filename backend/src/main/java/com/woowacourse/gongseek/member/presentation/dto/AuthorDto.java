package com.woowacourse.gongseek.member.presentation.dto;

import com.woowacourse.gongseek.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorDto {
    private String name;
    private String avatarUrl;

    public AuthorDto(Member member) {
        this.name = member.getName();
        this.avatarUrl = member.getAvatarUrl();
    }
}
