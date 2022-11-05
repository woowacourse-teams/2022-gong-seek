package com.woowacourse.gongseek.article.domain.repository;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.comment.domain.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPagePreviewDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPagePreviewDto> findAllByMemberIdWithCommentCount(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                MyPagePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.category,
                                count(comment),
                                article.views.value,
                                article.createdAt,
                                article.updatedAt
                        )
                )
                .from(article)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .where(article.member.id.eq(memberId))
                .groupBy(article.id)
                .fetch();
    }
}
