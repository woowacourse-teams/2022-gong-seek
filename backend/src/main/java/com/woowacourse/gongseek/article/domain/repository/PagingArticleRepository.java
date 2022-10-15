package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PagingArticleRepository {

    Slice<Article> findAllByPage(Long cursorId, Integer views, String category, String sortType, Pageable pageable);

    Slice<Article> findAllByLikes(Long cursorId, Long likes, String category, Pageable pageable);

    Slice<Article> searchByContainingText(Long cursorId, String searchText, Pageable pageable);

    Slice<Article> searchByAuthor(Long cursorId, String author, Pageable pageable);

    Slice<Article> searchByTag(Long cursorId, List<String> tagNames, Pageable pageable);
}
