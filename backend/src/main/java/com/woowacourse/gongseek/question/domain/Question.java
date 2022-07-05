package com.woowacourse.gongseek.question.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Question {

    private static final int MIN_TITLE_LENGTH = 0;
    private static final int MAX_TITLE_LENGTH = 500;
    private static final int MAX_CONTENT_LENGTH = 1000;

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

    private void validateTitleLength(String title) {
        if (title.trim().length() <= MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("타이틀의 길이는 0 이상 500 이하여야합니다.");
        }
    }

    private void validateContentLength(String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("컨텐트의 길이는 1000 이하여야합니다.");
        }
    }
}
