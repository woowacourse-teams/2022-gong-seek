package com.woowacourse.gongseek.like.domain.repository;

import com.woowacourse.gongseek.like.domain.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByArticleIdAndMemberId(Long articleId, Long memberId);

    boolean existsByArticleIdAndMemberId(Long id, Long id1);
}
