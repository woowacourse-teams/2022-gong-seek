package com.woowacourse.gongseek.article.infra.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.woowacourse.gongseek.article.domain.QArticle.article;
import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;
import static com.woowacourse.gongseek.tag.domain.QTag.tag;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.repository.ArticleTagRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ArticleTagRepositoryImpl implements ArticleTagRepository {

    private final JPAQueryFactory queryFactory;

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
