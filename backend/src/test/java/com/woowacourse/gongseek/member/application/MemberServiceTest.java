package com.woowacourse.gongseek.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.commons.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;

    @BeforeEach
    void setUp() {
        databaseCleaner.tableClear();
        member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
    }

    @Test
    void 회원은_회원_정보를_조회한다() {
        MemberDto memberDto = memberService.getOne(new LoginMember(member.getId()));

        assertAll(
                () -> assertThat(memberDto.getName()).isEqualTo(member.getName()),
                () -> assertThat(memberDto.getAvatarUrl()).isEqualTo(member.getAvatarUrl())
        );
    }

    @Test
    void 비회원은_회원_정보를_조회할_수_없다() {
        assertThatThrownBy(() -> memberService.getOne(new GuestMember()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }
}
