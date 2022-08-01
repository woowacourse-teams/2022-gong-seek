package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.QArticle.article;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import java.util.List;
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
                        categoryEquals(category))
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

        return articleIdStatus(cursorId);
    }

    private BooleanExpression articleIdStatus(Long cursorId) {
        return cursorId == null ? null : article.id.lt(cursorId);
    }

    private BooleanExpression categoryEquals(String category) {
        return "all".equals(category) ? null : article.category.eq(Category.from(category));
    }
}
