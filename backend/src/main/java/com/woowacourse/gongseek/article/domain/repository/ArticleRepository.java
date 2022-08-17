package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.tag.domain.Name;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    List<Article> findAllByMemberIdIn(List<Long> memberIds);

    @Query("SELECT case WHEN (count(at) > 0) THEN true ELSE false END FROM ArticleTag at WHERE at.tag.name = :name")
    boolean existsByTagName(@Param("name") Name name);
}
