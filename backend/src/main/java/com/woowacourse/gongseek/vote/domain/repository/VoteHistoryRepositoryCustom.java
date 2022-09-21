package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.Optional;

public interface VoteHistoryRepositoryCustom {

    Optional<VoteHistory> findByVoteIdAndMemberId(Long voteId, Long memberId);
}
