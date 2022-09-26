package com.woowacourse.gongseek.auth.domain.repository;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {

    List<RefreshToken> findAllByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
