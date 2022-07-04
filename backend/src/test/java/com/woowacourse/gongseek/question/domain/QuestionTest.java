package com.woowacourse.gongseek.question.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuestionTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 타이틀이_빈_경우(String title) {
        String content = "content";

        assertThatThrownBy(() -> new Question(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }

    @Test
    void 타이틀이_500자를_초과한_경우() {
        String title = "t".repeat(501);
        String content = "content";

        assertThatThrownBy(() -> new Question(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("타이틀의 길이는 0 이상 500 이하여야합니다.");
    }

    @Test
    void 컨텐트가_1000자를_초과한_경우() {
        String title = "title";
        String content = "c".repeat(1001);

        assertThatThrownBy(() -> new Question(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("컨텐트의 길이는 1000 이하여야합니다.");
    }
}
