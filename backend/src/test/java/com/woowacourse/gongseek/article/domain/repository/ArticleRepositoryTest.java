package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void 질문을_저장한다() {
        String title = "질문합니다.";
        String content = "내용입나다....";

        Article article = new Article(title, content, Category.QUESTION);
        Article savedArticle = articleRepository.save(article);

        assertAll(
                () -> assertThat(savedArticle.getTitle()).isEqualTo(article.getTitle()),
                () -> assertThat(savedArticle.getContent()).isEqualTo(article.getContent()),
                () -> assertThat(savedArticle.getCategory()).isEqualTo(article.getCategory())
        );
    }
}
