package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

    Optional<ArticleDto> findByIdWithAll(Long id, Long memberId);

    List<MyPageArticleDto> findAllByMemberIdWithCommentCount(Long memberId);

    boolean existsArticleByTagId(Long tagId);

    Slice<Article> findAllByPage(Long cursorId, Integer views, String category, String sortType, Pageable pageable);

    Slice<Article> findAllByLikes(Long cursorId, Long likes, String category, Pageable pageable);

    Slice<ArticlePreviewDto> searchByContainingText(Long cursorId, String searchText, Long memberId, Pageable pageable);

    Slice<Article> searchByAuthor(Long cursorId, String author, Pageable pageable);

    Slice<Article> searchByTag(Long cursorId, List<String> tagNames, Pageable pageable);
}
