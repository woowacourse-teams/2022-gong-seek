package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;
import static com.woowacourse.gongseek.like.domain.QLike.like;
import static com.woowacourse.gongseek.member.domain.QMember.member;
import static com.woowacourse.gongseek.tag.domain.QTag.tag;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import java.util.List;
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
    public Slice<ArticlePreviewDto> findAllByPage(Long cursorId, Long views, String category, String sortType,
                                                  Long memberId, Pageable pageable) {
        JPAQuery<ArticlePreviewDto> query = queryFactory
                .select(
                        Projections.constructor(
                                ArticlePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.member,
                                article.content.value,
                                article.category,
                                article.isAnonymous,
                                article.views.value,
                                article.commentCount.value,
                                article.likeCount.value,
                                isLike(article.id, memberId),
                                article.createdAt
                        )
                )
                .from(article)
                .join(article.member, member)
                .where(
                        cursorIdAndCursorViews(cursorId, views, sortType),
                        categoryEquals(category)
                )
                .limit(pageable.getPageSize() + 1);
        List<ArticlePreviewDto> fetch = sort(sortType, query);

        return convertToSliceFromArticle(fetch, pageable);
    }

    private BooleanExpression isLike(NumberPath<Long> articleId, Long memberId) {
        if (memberId.equals(0L)) {
            return Expressions.FALSE;
        }
        return JPAExpressions.selectOne()
                .from(like)
                .where(eqLike(articleId, memberId))
                .exists();
    }

    private BooleanExpression eqLike(NumberPath<Long> articleId, Long memberId) {
        return like.article.id.eq(articleId).and(like.member.id.eq(memberId));
    }

    private BooleanExpression cursorIdAndCursorViews(Long cursorId, Long cursorViews, String sortType) {
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

    private BooleanExpression categoryEquals(String category) {
        return "all".equals(category) ? null : article.category.eq(Category.from(category));
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

    @Override
    public Slice<ArticlePreviewDto> findAllByLikes(Long cursorId, Long cursorLike, String category, Long memberId,
                                                   Pageable pageable) {
        List<ArticlePreviewDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                ArticlePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.member,
                                article.content.value,
                                article.category,
                                article.isAnonymous,
                                article.views.value,
                                article.commentCount.value,
                                article.likeCount.value,
                                isLike(article.id, memberId),
                                article.createdAt
                        )
                )
                .from(article)
                .join(article.member, member)
                .where(
                        cursorIdAndLikes(cursorId, cursorLike),
                        categoryEquals(category)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.likeCount.value.desc(), article.id.desc())
                .fetch();

        return convertToSliceFromArticle(fetch, pageable);
    }

    private BooleanExpression cursorIdAndLikes(Long cursorId, Long likes) {
        if (cursorId == null || likes == null) {
            return null;
        }
        return article.likeCount.value.eq(likes)
                .and(article.id.lt(cursorId))
                .or(article.likeCount.value.lt(likes));
    }

    @Override
    public Slice<ArticlePreviewDto> searchByContainingText(Long cursorId, String searchText, Long memberId,
                                                           Pageable pageable) {
        List<ArticlePreviewDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                ArticlePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.member,
                                article.content.value,
                                article.category,
                                article.isAnonymous,
                                article.views.value,
                                article.commentCount.value,
                                article.likeCount.value,
                                isLike(article.id, memberId),
                                article.createdAt
                        )
                )
                .from(article)
                .leftJoin(article.member, member)
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
        String text = searchText.toLowerCase().replace(" ", "");
        StringExpression title = Expressions.stringTemplate("replace({0},' ','')", article.title.value).lower();
        StringExpression content = Expressions.stringTemplate("replace({0},' ','')", article.content.value).lower();
        return title.contains(text)
                .or(content.contains(text));
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
        List<ArticlePreviewDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                ArticlePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.member,
                                article.content.value,
                                article.category,
                                article.isAnonymous,
                                article.views.value,
                                article.commentCount.value,
                                article.likeCount.value,
                                isLike(article.id, memberId),
                                article.createdAt
                        )
                )
                .from(article)
                .leftJoin(article.member, member)
                .where(
                        article.member.name.value.eq(author),
                        article.isAnonymous.eq(false),
                        isOverArticleId(cursorId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();

        return convertToSliceBySearch(fetch, pageable);
    }

    @Override
    public Slice<ArticlePreviewDto> searchByTag(Long cursorId, Long memberId, List<String> tagNames,
                                                Pageable pageable) {
        List<ArticlePreviewDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                ArticlePreviewDto.class,
                                article.id,
                                article.title.value,
                                article.member,
                                article.content.value,
                                article.category,
                                article.isAnonymous,
                                article.views.value,
                                article.commentCount.value,
                                article.likeCount.value,
                                isLike(article.id, memberId),
                                article.createdAt
                        )
                )
                .from(articleTag)
                .join(articleTag.article, article)
                .join(article.member, member)
                .join(articleTag.tag, tag)
                .where(
                        articleTag.tag.name.in(getUpperTagNames(tagNames)),
                        isOverArticleId(cursorId)
                )
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
