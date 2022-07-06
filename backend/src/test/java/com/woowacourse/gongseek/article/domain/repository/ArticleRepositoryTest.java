package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void 질문을_저장한다() {
        String title = "질문합니다.";
        String content = "내용입나다....";

        Article article = new Article(title, content, Category.QUESTION);
        Article savedArticle = articleRepository.save(article);

        assertThat(savedArticle).isSameAs(article);
    }
}
