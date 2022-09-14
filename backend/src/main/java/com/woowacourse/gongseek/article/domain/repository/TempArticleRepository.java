package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.TempArticle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempArticleRepository extends JpaRepository<TempArticle, Long> {

    List<TempArticle> findAllByMemberId(final Long id);

    Optional<TempArticle> findByIdAndMemberId(final Long tempArticleId, final Long memberId);

    boolean existsByIdAndMemberId(final Long tempArticleId, final Long memberId);
}
