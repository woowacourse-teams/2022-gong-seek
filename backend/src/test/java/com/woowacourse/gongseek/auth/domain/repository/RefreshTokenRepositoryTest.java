package com.woowacourse.gongseek.auth.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
    }

    @Test
    void 리프레시토큰의_값으로_리프레시토큰을_찾는다() {
        final Member member = memberRepository.save(new Member("giron", "gitID", "avatar.url"));
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.create(member.getId()));

        assertThat(refreshTokenRepository.findById(refreshToken.getId())).isNotEmpty();
    }

    @Test
    void 유저의_아이디로_리프레시토큰을_삭제한다() {
        final Member member = memberRepository.save(new Member("giron", "gitID", "avatar.url"));
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.create(member.getId()));

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByMemberId(member.getId());
        refreshTokenRepository.deleteById(refreshTokens.get(0).getId());

        assertAll(
                () -> assertThat(refreshTokens).hasSize(1),
                () -> assertThat(refreshTokenRepository.findById(refreshToken.getId())).isEmpty()
        );
    }

    @Test
    void 특정_유저의_리프레시토큰만_전부_삭제한다() {
        final Member giron = memberRepository.save(new Member("giron", "gitID", "avatar.url"));
        RefreshToken gironRefreshToken = refreshTokenRepository.save(RefreshToken.create(giron.getId()));

        final Member slo = memberRepository.save(new Member("slo", "gitID", "avatar.url"));
        RefreshToken sloRefreshToken = refreshTokenRepository.save(RefreshToken.create(slo.getId()));

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByMemberId(giron.getId());
        refreshTokenRepository.deleteAll(refreshTokens);

        assertAll(
                () -> assertThat(refreshTokenRepository.findById(gironRefreshToken.getId())).isEmpty(),
                () -> assertThat(refreshTokenRepository.findById(sloRefreshToken.getId())).isNotEmpty()
        );
    }
}
