package com.woowacourse.gongseek.auth.domain.repository;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    void deleteAllByMemberId(Long memberId);
}
