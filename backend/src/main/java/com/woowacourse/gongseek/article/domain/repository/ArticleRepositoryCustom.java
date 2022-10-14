package com.woowacourse.gongseek.article.domain.repository;

import java.util.List;
import java.util.Map;

public interface ArticleRepositoryCustom {

    boolean existsArticleByTagId(Long tagId);

    Map<Long, List<String>> findTags(List<Long> articleIds);
}
