package com.woowacourse.gongseek.article.domain.repository;

import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private List<String> getTagNames(List<ArticleTag> articleTags) {
        return articleTags.stream()
                .map(articleTag -> articleTag.getTag().getName())
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsArticleByTagId(Long tagId) {
        return queryFactory.selectOne()
                .from(articleTag)
                .where(articleTag.tag.id.eq(tagId))
                .fetchFirst() != null;
    }
}
