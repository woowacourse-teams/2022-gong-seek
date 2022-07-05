package com.woowacourse.gongseek.question.domain.repository;

import com.woowacourse.gongseek.question.domain.Question;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class QuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Question save(Question question) {
        entityManager.persist(question);
        return question;
    }
}
