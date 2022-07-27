package com.woowacourse.gongseek.article.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class Views {

    @Column(name = "views", nullable = false)
    private int value;

    public Views(int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("조회수는 음수일 수 없습니다.");
        }
    }

    public void addValue() {
        value++;
    }
}
