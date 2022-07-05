package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class QuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Article save(Article article) {
        entityManager.persist(article);
        return article;
    }
}
