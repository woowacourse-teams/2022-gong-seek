package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void 질문을_저장한다() {
        String title = "질문합니다.";
        String content = "내용입나다....";
        String category = "question";

        ArticleRequest question = new ArticleRequest(title, content, category);
        ArticleIdResponse articleIdResponse = articleService.save(question);

        assertThat(articleIdResponse.getId()).isNotNull();
    }
}
