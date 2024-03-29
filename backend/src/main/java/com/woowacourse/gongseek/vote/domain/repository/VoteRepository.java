package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.Vote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByArticleId(Long articleId);

    Optional<Vote> findByArticleId(Long articleId);
}
