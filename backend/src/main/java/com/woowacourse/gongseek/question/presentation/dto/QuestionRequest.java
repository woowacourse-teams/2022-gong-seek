package com.woowacourse.gongseek.question.presentation.dto;

public class QuestionRequest {

    private String title;
    private String content;

    private QuestionRequest() {
    }

    public QuestionRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
