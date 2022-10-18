package com.woowacourse.gongseek.vote.domain.repository;

import static com.woowacourse.gongseek.vote.domain.QVoteHistory.voteHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoteHistoryRepositoryImpl implements VoteHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<VoteHistory> findByVoteIdAndMemberId(Long voteItemId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(voteHistory)
                .where(
                        voteHistory.voteItemId.eq(voteItemId)
                                .and(voteHistory.member.id.eq(memberId))
                )
                .fetchFirst());
    }
}
