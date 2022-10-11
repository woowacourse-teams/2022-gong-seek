package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleRepositoryCustom {

    List<MyPageArticleDto> findAllByMemberIdWithCommentCount(Long memberId);

    Optional<ArticleDto> findByIdWithAll(Long id, Long memberId);

    List<String> findTagNamesByArticleId(Long articleId);

    boolean existsArticleByTagId(Long tagId);

    Map<Long, List<String>> findTags(List<Long> articleIds);
}
