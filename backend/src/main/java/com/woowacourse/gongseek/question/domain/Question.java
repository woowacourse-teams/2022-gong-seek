package com.woowacourse.gongseek.question.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    protected Question() {
    }

    public Question(String title, String content) {
        validateTitleLength(title);
        validateContentLength(content);
        this.title = title;
        this.content = content;
    }

    public void validateTitleLength(String title) {
        if (title.trim().length() <= 0 || title.length() > 500) {
            throw new IllegalArgumentException("타이틀의 길이는 0 이상 500 이하여야합니다.");
        }
    }

    public void validateContentLength(String content) {
        if (content.length() > 1000) {
            throw new IllegalArgumentException("컨텐트의 길이는 1000 이하여야합니다.");
        }
    }

}
