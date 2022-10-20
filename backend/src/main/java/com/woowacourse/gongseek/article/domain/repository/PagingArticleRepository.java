package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PagingArticleRepository {

    Slice<ArticlePreviewDto> findAllByPage(Long cursorId, Long views, String category, String sortType, Long memberId,
                                           Pageable pageable);

    Slice<ArticlePreviewDto> findAllByLikes(Long cursorId, Long cursorLikes, String category, Long memberId,
                                            Pageable pageable);

    Slice<ArticlePreviewDto> searchByContainingText(Long cursorId, String searchText, Long memberId, Pageable pageable);

    Slice<ArticlePreviewDto> searchByAuthor(Long cursorId, String author, Long memberId, Pageable pageable);

    Slice<ArticlePreviewDto> searchByTag(Long cursorId, Long memberId, List<String> tagNames, Pageable pageable);
}
