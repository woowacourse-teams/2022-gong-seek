package com.woowacourse.gongseek.member.presentation.dto;

import com.woowacourse.gongseek.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberDto {

    private String name;
    private String avatarUrl;

    public MemberDto(Member member) {
        this.name = member.getName();
        this.avatarUrl = member.getAvatarUrl();
    }
}
