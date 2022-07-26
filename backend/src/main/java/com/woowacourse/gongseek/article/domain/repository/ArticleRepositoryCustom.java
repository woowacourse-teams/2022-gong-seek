package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findAllByPage(Long cursorId, String category, String sortType, int pageSize);
}
