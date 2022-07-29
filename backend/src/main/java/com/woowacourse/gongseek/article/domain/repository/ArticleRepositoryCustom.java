package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findAllByPage(Long cursorId, Integer views, String category, String sortType, int pageSize);

    List<Article> searchByTitleOrContentLike(Long cursorId, String searchText, int pageSize);
}
