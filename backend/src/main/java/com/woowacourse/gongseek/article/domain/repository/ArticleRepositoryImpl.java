package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;
import static com.woowacourse.gongseek.tag.domain.QTag.tag;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> findAllByPage(Long cursorId, Integer cursorViews, String category, String sortType,
                                       int pageSize) {
        JPAQuery<Article> query = queryFactory
                .selectFrom(article)
                .where(
                        cursorIdAndCursorViews(cursorId, cursorViews, sortType),
                        categoryEquals(category)
                )
                .limit(pageSize + 1);

        if (sortType.equals("views")) {
            return query.orderBy(article.views.value.desc(), article.id.desc()).fetch();
        }
        return query.orderBy(article.id.desc()).fetch();
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

    private BooleanExpression categoryEquals(String category) {
        return "all".equals(category) ? null : article.category.eq(Category.from(category));
    }

    @Override
    public List<Article> searchByContainingText(Long cursorId, int pageSize, String searchText) {
        return queryFactory
                .selectFrom(article)
                .where(
                        containsTitleOrContent(searchText),
                        isOverArticleId(cursorId)
                )
                .limit(pageSize + 1)
                .orderBy(article.id.desc())
                .fetch();
    }

    private BooleanExpression containsTitleOrContent(String searchText) {
        String text = searchText.toLowerCase().replace(" ", "");
        StringExpression title = Expressions.stringTemplate("replace({0},' ','')", article.title.value).lower();
        StringExpression content = Expressions.stringTemplate("replace({0},' ','')", article.content.value).lower();
        return title.contains(text)
                .or(content.contains(text));
    }

    @Override
    public List<Article> searchByAuthor(Long cursorId, int pageSize, String author) {
        return queryFactory
                .selectFrom(article)
                .where(
                        article.member.name.value.eq(author),
                        isOverArticleId(cursorId)
                )
                .limit(pageSize + 1)
                .orderBy(article.id.desc())
                .fetch();
    }

    @Override
    public List<Article> searchByTag(Long cursorId, int pageSize, List<String> tagNames) {
        return queryFactory
                .select(article)
                .from(articleTag)
                .where(
                        articleTag.tag.name.in(getUpperTagNames(tagNames)),
                        isOverArticleId(cursorId)
                )
                .join(articleTag.article, article)
                .join(articleTag.tag, tag)
                .distinct()
                .limit(pageSize + 1)
                .orderBy(articleTag.article.id.desc())
                .fetch();
    }

    private List<String> getUpperTagNames(List<String> tagNames) {
        return tagNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
