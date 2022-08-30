package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteHistoryRepository extends JpaRepository<VoteHistory, Long> {

    Optional<VoteHistory> findByVoteIdAndMemberId(Long voteId, Long memberId);

    void deleteByVoteIdAndMemberId(Long voteId, Long memberId);

}
