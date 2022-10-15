package com.woowacourse.gongseek.article.domain.repository;

public interface ArticleRepositoryCustom {

    boolean existsArticleByTagId(Long tagId);
}
