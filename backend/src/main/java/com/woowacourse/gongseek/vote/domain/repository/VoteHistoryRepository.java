package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteHistoryRepository extends JpaRepository<VoteHistory, Long> {

    Optional<VoteHistory> findByVoteIdAndMemberId(Long voteId, Long memberId);

    void deleteByVoteIdAndMemberId(Long voteId, Long memberId);

    boolean existsByVoteIdAndMemberId(Long voteId, Long memberId);
}
