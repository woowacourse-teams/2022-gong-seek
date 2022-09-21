package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    @Query("select distinct a from Article a "
            + "join fetch a.member "
            + "left join fetch a.articleTags.value at "
            + "left join fetch at.tag "
            + "where a.id = :id")
    Optional<Article> findByIdWithAll(Long id);

    List<Article> findAllByMemberId(Long memberId);

    @Query("SELECT case WHEN (count(at) > 0) THEN true ELSE false END FROM ArticleTag at WHERE upper(at.tag.name) = upper(:name)")
    boolean existsArticleByTagName(@Param("name") String name);
}
