package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findAllByPage(Long cursorId, Integer views, String category, String sortType, int pageSize);

    List<Article> searchByContainingText(Long cursorId, int pageSize, String searchText);

    List<Article> searchByAuthor(Long cursorId, int pageSize, String author);

    List<Article> searchByTag(Long cursorId, int pageSize, List<String> tagNames);
}
