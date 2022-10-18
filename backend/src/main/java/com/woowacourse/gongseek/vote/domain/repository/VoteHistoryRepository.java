package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteHistoryRepository extends JpaRepository<VoteHistory, Long>, VoteHistoryRepositoryCustom {

    Optional<VoteHistory> findByVoteItemIdAndMemberId(Long voteItemId, Long memberId);

    void deleteAllByVoteItemIn(List<VoteItem> voteItems);
}
