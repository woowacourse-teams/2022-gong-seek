package com.woowacourse.gongseek.auth.domain.repository;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    //Optional<RefreshToken> findByToken(UUID refreshToken);

    void deleteAllByMemberId(Long memberId);
}
