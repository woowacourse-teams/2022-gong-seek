package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    List<ArticleTag> findAllByArticleId(Long articleId);
}
