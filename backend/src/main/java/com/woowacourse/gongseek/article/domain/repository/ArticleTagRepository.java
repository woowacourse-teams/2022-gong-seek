package com.woowacourse.gongseek.article.domain.repository;

public interface ArticleTagRepository {

    boolean existsArticleByTagId(Long tagId);
}
