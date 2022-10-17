package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CommentCountTest {

    @Test
    void 댓글수를_생성한다() {
        CommentCount commentCount = new CommentCount();

        assertThat(commentCount.getValue()).isEqualTo(0);
    }

    @Test
    void 댓글수를_증가시킬_수_있다() {
        CommentCount commentCount = new CommentCount();

        commentCount.addValue();

        assertThat(commentCount.getValue()).isEqualTo(1);
    }

    @Test
    void 댓글수를_감소시킬_수_있다() {
        CommentCount commentCount = new CommentCount();

        commentCount.addValue();
        commentCount.addValue();
        commentCount.minusValue();

        assertThat(commentCount.getValue()).isEqualTo(1);
    }
}
