package com.woowacourse.gongseek.article.domain.repository;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.comment.domain.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MyPageArticleRepositoryImpl implements MyPageArticleRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPageArticleDto> findAllByMemberIdWithCommentCount(Long memberId) {
        return queryFactory
                .select(Projections.constructor(
                        MyPageArticleDto.class,
                        article.id,
                        article.title.value,
                        article.category,
                        count(comment.id),
                        article.views.value,
                        article.createdAt,
                        article.updatedAt)
                )
                .from(article)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .where(article.member.id.eq(memberId))
                .groupBy(article)
                .fetch();
    }
}
