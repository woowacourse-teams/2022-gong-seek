package com.woowacourse.gongseek.vote.domain.repository;

import static com.woowacourse.gongseek.vote.domain.QVote.vote;
import static com.woowacourse.gongseek.vote.domain.QVoteHistory.voteHistory;
import static com.woowacourse.gongseek.vote.domain.QVoteItem.voteItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoteHistoryRepositoryImpl implements VoteHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Optional<VoteHistory> findByVoteItemsAndMemberId(List<VoteItem> voteItems, Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(voteHistory)
                .where(
                        voteHistory.voteItem.in(voteItems)
                                .and(voteHistory.member.id.eq(memberId))
                )
                .fetchFirst());
    }

    @Override
    public Optional<VoteHistory> findByVoteIdInAndMemberId(Long voteId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(voteHistory)
                .join(voteHistory.voteItem, voteItem).fetchJoin()
                .join(voteItem.vote, vote).fetchJoin()
                .where(
                        voteItem.vote.id.eq(voteId)
                                .and(voteHistory.member.id.eq(memberId))
                )
                .fetchFirst());
    }
}
