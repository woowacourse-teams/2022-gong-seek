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
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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

    @Override
    public Slice<ArticlePreviewDto> findAllByPage(Long cursorId, Integer cursorViews, String category, String sortType,
                                                  Long memberId, Pageable pageable) {
        JPAQuery<ArticlePreviewDto> query = selectArticlePreviewDto(cursorId, memberId)
                .from(article)
                .leftJoin(article.member, member)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .leftJoin(like).on(article.id.eq(like.article.id))
                .where(
                        cursorIdAndCursorViews(cursorId, cursorViews, sortType),
                        categoryEquals(category)
                )
                .groupBy(article)
                .limit(pageable.getPageSize() + 1);
        List<ArticlePreviewDto> fetch = sort(sortType, query);

        return convertToSliceFromArticle(fetch, pageable);
    }

    private JPAQuery<ArticlePreviewDto> selectArticlePreviewDto(Long articleId, Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ArticlePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.member.name.value,
                                article.member.avatarUrl,
                                article.content.value,
                                article.category,
                                count(comment.id),
                                article.views.value,
                                isLike(articleId, memberId),
                                count(like.id),
                                article.createdAt
                        )
                );
    }

    private BooleanExpression cursorIdAndCursorViews(Long cursorId, Integer cursorViews, String sortType) {
        if (sortType.equals("views")) {
            if (cursorId == null || cursorViews == null) {
                return null;
            }

            return article.views.value.eq(cursorViews)
                    .and(article.id.lt(cursorId))
                    .or(article.views.value.lt(cursorViews));
        }

        return isOverArticleId(cursorId);
    }

    private BooleanExpression isOverArticleId(Long cursorId) {
        return cursorId == null ? null : article.id.lt(cursorId);
    }

    private List<ArticlePreviewDto> sort(String sortType, JPAQuery<ArticlePreviewDto> query) {
        if (sortType.equals("views")) {
            return query.orderBy(article.views.value.desc(), article.id.desc()).fetch();
        }
        return query.orderBy(article.id.desc()).fetch();
    }

    private SliceImpl<ArticlePreviewDto> convertToSliceFromArticle(List<ArticlePreviewDto> fetch, Pageable pageable) {
        boolean hasNext = false;

        if (fetch.size() == pageable.getPageSize() + 1) {
            fetch.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(fetch, pageable, hasNext);
    }

    private BooleanExpression categoryEquals(String category) {
        return "all".equals(category) ? null : article.category.eq(Category.from(category));
    }

    @Override
    public Slice<ArticlePreviewDto> findAllByLikes(Long cursorId, Long cursorLikes, String category, Long memberId,
                                                   Pageable pageable) {
        List<ArticlePreviewDto> fetch = selectArticlePreviewDto(cursorId, memberId)
                .from(article)
                .leftJoin(article.member, member)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .leftJoin(like).on(article.id.eq(like.article.id))
                .where(categoryEquals(category))
                .groupBy(article)
                .having(cursorIdAndLikes(cursorId, cursorLikes))
                .limit(pageable.getPageSize() + 1)
                .orderBy(like.count().desc(), article.id.desc())
                .fetch();

        return convertToSliceFromArticle(fetch, pageable);
    }


    private BooleanExpression cursorIdAndLikes(Long cursorId, Long likes) {
        if (cursorId == null || likes == null) {
            return null;
        }
        return like.count().eq(likes)
                .and(article.id.lt(cursorId))
                .or(like.count().lt(likes));
    }

    @Override
    public Slice<ArticlePreviewDto> searchByContainingText(Long cursorId, String searchText, Long memberId,
                                                           Pageable pageable) {
        List<ArticlePreviewDto> fetch = selectArticlePreviewDto(cursorId, memberId)
                .from(article)
                .leftJoin(article.member, member)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .leftJoin(like).on(article.id.eq(like.article.id))
                .where(
                        containsTitleOrContent(searchText),
                        isOverArticleId(cursorId)
                )
                .groupBy(article)
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();
        return convertToSliceBySearch(fetch, pageable);
    }

    private BooleanExpression containsTitleOrContent(String searchText) {
        return article.title.value.contains(searchText)
                .or(article.content.value.contains(searchText));
    }

    private SliceImpl<ArticlePreviewDto> convertToSliceBySearch(List<ArticlePreviewDto> fetch, Pageable pageable) {
        boolean hasNext = false;

        if (fetch.size() == pageable.getPageSize() + 1) {
            fetch.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(fetch, pageable, hasNext);
    }

    @Override
    public Slice<ArticlePreviewDto> searchByAuthor(Long cursorId, String author, Long memberId, Pageable pageable) {
        List<ArticlePreviewDto> fetch = selectArticlePreviewDto(cursorId, memberId)
                .from(article)
                .leftJoin(article.member, member)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .leftJoin(like).on(article.id.eq(like.article.id))
                .where(
                        article.member.name.value.eq(author),
                        article.isAnonymous.eq(false),
                        isOverArticleId(cursorId)
                )
                .groupBy(article)
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();
        return convertToSliceFromArticle(fetch, pageable);
    }

    @Override
    public Slice<ArticlePreviewDto> searchByTag(Long cursorId, Long memberId, List<String> tagNames,
                                                Pageable pageable) {
        List<ArticlePreviewDto> fetch = selectArticlePreviewDto(cursorId, memberId)
                .from(articleTag)
                .join(articleTag.article, article)
                .join(article.member, member)
                .join(articleTag.tag, tag)
                .leftJoin(comment).on(article.id.eq(comment.article.id))
                .leftJoin(like).on(article.id.eq(like.article.id))
                .where(
                        articleTag.tag.name.in(getUpperTagNames(tagNames)),
                        isOverArticleId(cursorId)
                )
                .groupBy(article)
                .limit(pageable.getPageSize() + 1)
                .orderBy(articleTag.article.id.desc())
                .fetch();
        return convertToSliceBySearch(fetch, pageable);
    }

    private List<String> getUpperTagNames(List<String> tagNames) {
        return tagNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
