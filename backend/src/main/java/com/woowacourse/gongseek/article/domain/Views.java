package com.woowacourse.gongseek.article.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Views {

    private static final int INITIAL_VIEWS = 0;

    @Column(name = "views", nullable = false)
    private long value;

    public Views() {
        this.value = INITIAL_VIEWS;
    }

    public void addValue() {
        value++;
    }
}
