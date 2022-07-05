package com.woowacourse.gongseek.question.presentation.dto;

import com.woowacourse.gongseek.question.domain.Question;
import lombok.Getter;

@Getter
public class QuestionRequest {

    private String title;
    private String content;

    private QuestionRequest() {
    }

    public QuestionRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Question toEntity(){
        return new Question(title, content);
    }
}
