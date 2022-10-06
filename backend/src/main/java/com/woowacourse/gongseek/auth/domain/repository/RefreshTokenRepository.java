package com.woowacourse.gongseek.auth.domain.repository;

import com.woowacourse.gongseek.auth.domain.RefreshToken;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    List<RefreshToken> findAllByMemberId(Long memberId);
}
