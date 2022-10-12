package com.woowacourse.gongseek.article.domain.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.ExpressionUtils.count;
import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;
import static com.woowacourse.gongseek.comment.domain.QComment.comment;
import static com.woowacourse.gongseek.like.domain.QLike.like;
import static com.woowacourse.gongseek.member.domain.QMember.member;
import static com.woowacourse.gongseek.tag.domain.QTag.tag;
import static com.woowacourse.gongseek.vote.domain.QVote.vote;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

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

    @Override
    public Optional<ArticleDto> findByIdWithAll(Long articleId, Long memberId) {
        List<String> tags = findTagNamesByArticleId(articleId);
        return Optional.ofNullable(
                queryFactory.select(
                                Projections.constructor(
                                        ArticleDto.class,
                                        article.title.value,
                                        Expressions.constant(tags),
                                        article.member.name.value,
                                        article.member.avatarUrl,
                                        article.content.value,
                                        article.member.id.eq(memberId),
                                        article.views.value,
                                        hasVote(articleId),
                                        isLike(articleId, memberId),
                                        article.isAnonymous,
                                        count(like.id),
                                        article.createdAt,
                                        article.updatedAt)
                        )
                        .from(article)
                        .join(article.member, member)
                        .leftJoin(like).on(article.id.eq(like.article.id))
                        .where(article.id.eq(articleId))
                        .groupBy(article)
                        .fetchOne());
    }

    @Override
    public List<String> findTagNamesByArticleId(Long articleId) {
        List<ArticleTag> tags = queryFactory.selectFrom(articleTag)
                .join(articleTag.tag).fetchJoin()
                .where(articleTag.article.id.eq(articleId))
                .fetch();

        return getTagNames(tags);
    }

    private List<String> getTagNames(List<ArticleTag> articleTags) {
        return articleTags.stream()
                .map(articleTag -> articleTag.getTag().getName())
                .collect(Collectors.toList());
    }

    private BooleanExpression hasVote(Long articleId) {
        return JPAExpressions.selectOne()
                .from(vote)
                .where(vote.article.id.eq(articleId))
                .exists();
    }

    private BooleanExpression isLike(Long articleId, Long memberId) {
        return JPAExpressions.selectOne()
                .from(like)
                .where(eqLike(articleId, memberId))
                .exists();
    }

    private BooleanExpression eqLike(Long articleId, Long memberId) {
        if (Objects.isNull(articleId) || memberId.equals(0L)) {
            return null;
        }
        return like.article.id.eq(articleId).and(like.member.id.eq(memberId));
    }

    @Override
    public boolean existsArticleByTagId(Long tagId) {
        return queryFactory.selectOne()
                .from(articleTag)
                .where(articleTag.tag.id.eq(tagId))
                .fetchFirst() != null;
    }

    @Override
    public Map<Long, List<String>> findTags(List<Long> articleIds) {
        return queryFactory
                .from(article)
                .leftJoin(article.articleTags.value, articleTag)
                .leftJoin(articleTag.tag, tag)
                .where(article.id.in(articleIds))
                .transform(groupBy(article.id).as(GroupBy.list(tag.name)));
    }
}
