package com.woowacourse.gongseek.vote.domain.repository;

import static com.woowacourse.gongseek.vote.domain.QVoteHistory.voteHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.Optional;
import javax.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;

@RequiredArgsConstructor
public class VoteHistoryRepositoryImpl implements VoteHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
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
