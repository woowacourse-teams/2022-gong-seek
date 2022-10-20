package com.woowacourse.gongseek.article.infra.repository;

import static com.woowacourse.gongseek.article.domain.articletag.QArticleTag.articleTag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.gongseek.article.domain.repository.ArticleTagRepository;
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
}
