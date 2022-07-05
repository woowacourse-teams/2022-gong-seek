package com.woowacourse.gongseek.question.presentation.dto;

import com.woowacourse.gongseek.question.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private final Long id;
    private final String title;
    private final String content;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
    }
}
