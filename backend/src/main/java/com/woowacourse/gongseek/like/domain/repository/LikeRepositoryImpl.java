package com.woowacourse.gongseek.like.domain.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.woowacourse.gongseek.like.domain.QLike.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.like.domain.Like;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Map<Long, List<Like>> findLikesByArticleIdsAndMemberId(List<Long> articleIds, Long memberId) {
        return queryFactory
                .from(like)
                .where(
                        like.article.id.in(articleIds),
                        like.member.id.eq(memberId)
                )
                .transform(groupBy(like.article.id).as(list(like)));
    }
}
