package com.woowacourse.gongseek.article.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class CommentCount {

    private static final int INITIAL_VIEWS = 0;

    @Column(name = "comment_count", nullable = false)
    private int value;

    public CommentCount() {
        this.value = INITIAL_VIEWS;
    }

    public void addValue() {
        value++;
    }

    public void minusValue() {
        value--;
    }
}
