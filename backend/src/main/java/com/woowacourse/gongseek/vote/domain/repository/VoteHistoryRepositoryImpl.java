package com.woowacourse.gongseek.vote.domain.repository;

import static com.woowacourse.gongseek.vote.domain.QVote.vote;
import static com.woowacourse.gongseek.vote.domain.QVoteHistory.voteHistory;
import static com.woowacourse.gongseek.vote.domain.QVoteItem.voteItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoteHistoryRepositoryImpl implements VoteHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<VoteHistory> findByVoteIdAndMemberId(Long voteId, Long memberId) {
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
