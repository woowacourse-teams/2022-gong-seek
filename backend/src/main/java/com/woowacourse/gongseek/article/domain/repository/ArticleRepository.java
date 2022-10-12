package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Article a SET a.views.value = a.views.value + 1 WHERE a.id = :articleId")
    void addViews(@Param("articleId") Long articleId);
}
