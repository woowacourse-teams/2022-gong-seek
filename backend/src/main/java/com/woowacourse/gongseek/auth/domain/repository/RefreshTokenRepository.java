package com.woowacourse.gongseek.auth.domain.repository;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByValueAndMemberId(String refreshToken, Long memberId);

    void deleteByMemberId(Long memberId);
}
