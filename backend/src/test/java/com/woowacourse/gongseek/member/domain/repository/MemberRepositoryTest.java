package com.woowacourse.gongseek.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 멤버를_저장한다() {
        Member member = new Member("name", "hanull", "https://avatars.githubusercontent.com/u/49219342?v=4");
        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isSameAs(member);
    }
}
