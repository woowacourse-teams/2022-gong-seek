package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.QArticle.article;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleAllResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> findAllByPage(Long cursorId, Integer cursorViews, String category,
                                       String sortType, int pageSize) {

        JPAQuery<Article> query = queryFactory
                .selectFrom(article)
                .where(
                        cursorIdAndCursorViews(cursorId, cursorViews, sortType),
                        categoryEq(category))
                .limit(pageSize + 1);

        if (sortType.equals("views")) {
            return query.orderBy(article.views.desc()).fetch();
        }
        return query.orderBy(article.id.desc()).fetch();
    }

    private BooleanExpression cursorIdAndCursorViews(Long cursorId, Integer cursorViews, String sortType) {
        if (sortType.equals("views")) {
            if (cursorId == null || cursorViews == null) {
                return null;
            }

            return article.views.eq(cursorViews)
                    .and(article.id.lt(cursorId))
                    .or(article.views.lt(cursorViews));
        }

        return ltArticleId(cursorId);
    }

    private BooleanExpression ltArticleId(Long cursorId) {
        return cursorId == null ? null : article.id.lt(cursorId);
    }

    private BooleanExpression categoryEq(String category) {
        return "all".equals(category) ? null : article.category.eq(Category.from(category));
    }
}
