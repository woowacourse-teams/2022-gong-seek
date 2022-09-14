package com.woowacourse.gongseek.auth.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 리프레시토큰의_값으로_리프레시토큰을_찾는다(){
        final Member member = memberRepository.save(new Member("giron", "gitID", "avatar.url"));
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.create(member.getId()));

        assertThat(refreshTokenRepository.findById(refreshToken.getId())).isNotEmpty();
    }

    @Test
    void 유저의_아이디로_리프레시토큰을_삭제한다(){
        final Member member = memberRepository.save(new Member("giron", "gitID", "avatar.url"));
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.create(member.getId()));
        refreshTokenRepository.deleteAllByMemberId(member.getId());

        assertThat(refreshTokenRepository.findById(refreshToken.getId())).isEmpty();
    }
}
