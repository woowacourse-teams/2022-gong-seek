package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

    List<MyPageArticleDto> findAllByMemberIdWithCommentCount(Long memberId);

    Slice<Article> findAllByPage(Long cursorId, Integer views, String category, String sortType, Pageable pageable);

    Slice<Article> findAllByLikes(Long cursorId, Long likes, String category, Pageable pageable);

    Slice<Article> searchByContainingText(Long cursorId, String searchText, Pageable pageable);

    Slice<Article> searchByAuthor(Long cursorId, String author, Pageable pageable);

    Slice<Article> searchByTag(Long cursorId, List<String> tagNames, Pageable pageable);
}
