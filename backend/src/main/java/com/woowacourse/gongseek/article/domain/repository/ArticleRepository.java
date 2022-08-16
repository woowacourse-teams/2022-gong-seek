package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    List<Article> findAllByMemberIdIn(List<Long> memberIds);

    @Query("SELECT case WHEN (count(at) > 0) THEN true ELSE false END FROM ArticleTag at WHERE at.tag.name = :name")
    boolean existsByTagName(String name);

    @Query("SELECT DISTINCT at.article FROM ArticleTag at WHERE at.tag.name IN :names")
    List<Article> findAllByTagNameIn(List<String> names);
}
