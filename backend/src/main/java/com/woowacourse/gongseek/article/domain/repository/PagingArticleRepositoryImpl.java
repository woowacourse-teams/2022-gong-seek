package com.woowacourse.gongseek.article.domain.repository;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;
import static com.woowacourse.gongseek.comment.domain.QComment.comment;
import static com.woowacourse.gongseek.like.domain.QLike.like;
import static com.woowacourse.gongseek.member.domain.QMember.member;
import static com.woowacourse.gongseek.tag.domain.QTag.tag;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PagingArticleRepositoryImpl implements PagingArticleRepository {

    private final JPAQueryFactory queryFactory;

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