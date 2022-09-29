package com.woowacourse.gongseek.article.domain.repository;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;
import static com.woowacourse.gongseek.comment.domain.QComment.comment;
import static com.woowacourse.gongseek.like.domain.QLike.like;
import static com.woowacourse.gongseek.member.domain.QMember.member;
import static com.woowacourse.gongseek.tag.domain.QTag.tag;
import static com.woowacourse.gongseek.vote.domain.QVote.vote;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
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
        List<ArticleTag> articleTags = getArticleTags(articleId);
        List<String> tagNames = articleTags.stream()
                .map(articleTag -> articleTag.getTag().getName())
                .collect(Collectors.toList());

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(
                                ArticleDto.class,
                                article.title.value,
                                Expressions.constant(tagNames),
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
                                article.updatedAt
                        )
                )
                .from(article)
                .join(article.member, member)
                .leftJoin(like).on(article.id.eq(like.article.id))
                .where(article.id.eq(articleId))
                .groupBy(article.id)
                .fetchOne());
    }

    private List<ArticleTag> getArticleTags(Long articleId) {
        return queryFactory.selectFrom(articleTag)
                .join(articleTag.tag, tag).fetchJoin()
                .where(articleTag.article.id.eq(articleId))
                .fetch();
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
                .where(like.article.id.eq(articleId).and(like.member.id.eq(memberId)))
                .exists();
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
                .groupBy(article.id)
                .fetch();
    }

    @Override
    public boolean existsArticleByTagId(Long tagId) {
        Integer tagCount = queryFactory.selectOne()
                .from(articleTag)
                .where(articleTag.tag.id.eq(tagId))
                .fetchFirst();

        return tagCount != null;
    }

    @Override
    public Slice<Article> findAllByPage(Long cursorId, Integer cursorViews, String category, String sortType,
                                        Pageable pageable) {
        JPAQuery<Article> query = queryFactory
                .selectFrom(article)
                .leftJoin(article.member, member).fetchJoin()
                .where(
                        cursorIdAndCursorViews(cursorId, cursorViews, sortType),
                        categoryEquals(category)
                )
                .limit(pageable.getPageSize() + 1);
        List<Article> fetch = sort(sortType, query);

        return convertToSlice(fetch, pageable);
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

    private List<Article> sort(String sortType, JPAQuery<Article> query) {
        if (sortType.equals("views")) {
            return query.orderBy(article.views.value.desc(), article.id.desc()).fetch();
        }
        return query.orderBy(article.id.desc()).fetch();
    }

    private SliceImpl<Article> convertToSlice(List<Article> fetch, Pageable pageable) {
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
    public Slice<Article> findAllByLikes(Long cursorId, Long cursorLikes, String category, Pageable pageable) {
        List<Article> fetch = queryFactory
                .select(article)
                .from(like)
                .rightJoin(like.article, article)
                .where(categoryEquals(category))
                .groupBy(article)
                .having(cursorIdAndLikes(cursorId, cursorLikes))
                .limit(pageable.getPageSize() + 1)
                .orderBy(like.count().desc(), article.id.desc())
                .fetch();

        return convertToSlice(fetch, pageable);
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
    public Slice<Article> searchByContainingText(Long cursorId, String searchText, Pageable pageable) {
        List<Article> fetch = queryFactory
                .selectFrom(article)
                .leftJoin(article.member, member).fetchJoin()
                .where(
                        containsTitleOrContent(searchText),
                        isOverArticleId(cursorId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();
        return convertToSlice(fetch, pageable);
    }

    private BooleanExpression containsTitleOrContent(String searchText) {
        String text = searchText.toLowerCase().replace(" ", "");
        StringExpression title = Expressions.stringTemplate("replace({0},' ','')", article.title.value).lower();
        StringExpression content = Expressions.stringTemplate("replace({0},' ','')", article.content.value).lower();
        return title.contains(text)
                .or(content.contains(text));
    }

    @Override
    public Slice<Article> searchByAuthor(Long cursorId, String author, Pageable pageable) {
        List<Article> fetch = queryFactory
                .selectFrom(article)
                .leftJoin(article.member, member).fetchJoin()
                .where(
                        article.member.name.value.eq(author),
                        article.isAnonymous.eq(false),
                        isOverArticleId(cursorId)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetch();
        return convertToSlice(fetch, pageable);
    }

    @Override
    public Slice<Article> searchByTag(Long cursorId, List<String> tagNames, Pageable pageable) {
        List<Article> fetch = queryFactory
                .select(article)
                .from(articleTag)
                .where(
                        articleTag.tag.name.in(getUpperTagNames(tagNames)),
                        isOverArticleId(cursorId)
                )
                .join(articleTag.article, article)
                .join(articleTag.tag, tag)
                .distinct()
                .limit(pageable.getPageSize() + 1)
                .orderBy(articleTag.article.id.desc())
                .fetch();
        return convertToSlice(fetch, pageable);
    }

    private List<String> getUpperTagNames(List<String> tagNames) {
        return tagNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
