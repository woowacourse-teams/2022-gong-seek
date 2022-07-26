package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.QArticle.article;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
    public List<Article> findAllByPage(Long cursorId, String category, String sortType, int pageSize) {
        JPAQuery<Article> query = queryFactory
                .selectFrom(article)
                .where(
                        ltArticleId(cursorId),
                        categoryEq(category))
                .limit(pageSize + 1);
        if (query.fetch().isEmpty()) {
            return query.fetch();
        }

        return query
                .orderBy(sort(sortType))
                .fetch();
    }

    private BooleanExpression ltArticleId(Long cursorId) {
        return cursorId == null ? null : article.id.lt(cursorId);
    }

    private BooleanExpression categoryEq(String category) {
        return "all".equals(category) ? null : article.category.eq(Category.from(category));
    }

    private OrderSpecifier<?> sort(String sortType) {
        switch (sortType) {
            case "latest":
                return new OrderSpecifier<>(Order.DESC, article.id);
            case "views":
                return new OrderSpecifier<>(Order.DESC, article.views);
            default:
                return null;
        }
    }

}
