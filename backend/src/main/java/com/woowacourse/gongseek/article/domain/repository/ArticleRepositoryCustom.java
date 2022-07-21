package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {

    List<Article> findAll(String category, String sort, Pageable pageable);
}
