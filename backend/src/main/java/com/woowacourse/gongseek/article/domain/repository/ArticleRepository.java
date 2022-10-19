package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select distinct a from Article a "
            + "join fetch a.member "
            + "left join fetch a.articleTags.value at "
            + "left join fetch at.tag "
            + "where a.id = :id")
    Optional<Article> findByIdWithAll(@Param("id") Long id);

    List<Article> findAllByMemberId(Long memberId);
}
