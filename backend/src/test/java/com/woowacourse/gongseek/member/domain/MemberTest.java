package com.woowacourse.gongseek.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 깃허브_프로필을_수정한다() {
        String updatedAvatarUrl = "avatar.update";
        Member member = new Member("홍길동", "123", "avatar.example");
        member.updateAvatarUrl(updatedAvatarUrl);

        assertThat(member.getAvatarUrl()).isEqualTo(updatedAvatarUrl);
    }

    @Test
    void 이름을_수정한다() {
        String updatedName = "기론";
        Member member = new Member("홍길동", "123", "avatar.example");
        member.updateName(updatedName);

        assertThat(member.getName()).isEqualTo(updatedName);
    }
}
