package com.woowacourse.gongseek.vote.domain.repository;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.woowacourse.gongseek.vote.domain.QVote.vote;
import static com.woowacourse.gongseek.vote.domain.QVoteHistory.voteHistory;
import static com.woowacourse.gongseek.vote.domain.QVoteItem.voteItem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.vote.domain.repository.dto.VoteItemDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoteItemRepositoryImpl implements VoteItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<VoteItemDto> findAllByArticleIdWithCount(Long articleId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                VoteItemDto.class,
                                voteItem.id,
                                voteItem.content.value,
                                count(voteHistory)
                        )
                )
                .from(voteItem)
                .join(voteItem.vote, vote)
                .leftJoin(voteHistory).on(voteItem.id.eq(voteHistory.voteItem.id))
                .where(vote.article.id.eq(articleId))
                .groupBy(voteItem.id)
                .fetch();
    }
}
