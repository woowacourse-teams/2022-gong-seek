package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.util.List;
import java.util.Optional;

public interface VoteHistoryRepositoryCustom {

    Optional<VoteHistory> findByVoteItemsAndMemberId(List<VoteItem> voteItems, Long memberId);

    Optional<VoteHistory> findByVoteIdInAndMemberId(Long voteItemId, Long memberId);
}
