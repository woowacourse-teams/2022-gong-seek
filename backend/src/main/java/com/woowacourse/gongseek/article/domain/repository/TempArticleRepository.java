package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.TempArticle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempArticleRepository extends JpaRepository<TempArticle, Long> {

    List<TempArticle> findAllByMemberId(Long id);
}
