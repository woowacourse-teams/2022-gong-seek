package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

    List<Article> findAllByPage(Long cursorId, Integer views, String category, String sortType, int pageSize);

    Slice<Article> findAllByLikes(Long cursorId, Long likes, String category, Pageable pageable);

    List<Article> searchByContainingText(Long cursorId, int pageSize, String searchText);

    List<Article> searchByAuthor(Long cursorId, int pageSize, String author);
}
