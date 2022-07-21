package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> findAll(String category, String sortType, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(article)
                .where(categoryEq(category))
                .orderBy(sort(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression categoryEq(String category) {
        return hasText(category) ? article.category.eq(Category.from(category)) : null;
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
