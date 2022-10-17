package com.woowacourse.gongseek.article.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class LikeCount {

    private static final int INITIAL_VIEWS = 0;

    @Column(name = "like_count", nullable = false)
    private long value;

    public LikeCount() {
        this.value = INITIAL_VIEWS;
    }

    public void addValue() {
        value++;
    }

    public void minusValue() {
        value--;
    }

    public void updateValue(long value) {
        this.value = value;
    }
}
