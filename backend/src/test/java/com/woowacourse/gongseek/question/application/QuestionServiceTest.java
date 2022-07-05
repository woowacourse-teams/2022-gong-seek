package com.woowacourse.gongseek.question.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.question.presentation.dto.QuestionRequest;
import com.woowacourse.gongseek.question.presentation.dto.QuestionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void 질문을_저장한다() {
        String title = "질문합니다.";
        String content = "내용입나다....";
        QuestionRequest question = new QuestionRequest(title, content);
        QuestionResponse questionResponse = questionService.save(question);
        assertAll(
                () -> assertThat(questionResponse.getTitle()).isEqualTo(title),
                () -> assertThat(questionResponse.getContent()).isEqualTo(content)
        );
    }
}
