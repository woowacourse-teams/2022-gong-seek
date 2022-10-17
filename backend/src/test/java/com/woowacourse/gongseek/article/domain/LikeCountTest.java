package com.woowacourse.gongseek.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class LikeCountTest {

    @Test
    void 좋아요수를_생성한다() {
        LikeCount likeCount = new LikeCount();

        assertThat(likeCount.getValue()).isEqualTo(0);
    }

    @Test
    void 좋아요수를_증가시킬_수_있다() {
        LikeCount likeCount = new LikeCount();

        likeCount.addValue();

        assertThat(likeCount.getValue()).isEqualTo(1);
    }

    @Test
    void 좋아요수를_감소시킬_수_있다() {
        LikeCount likeCount = new LikeCount();

        likeCount.addValue();
        likeCount.addValue();
        likeCount.minusValue();

        assertThat(likeCount.getValue()).isEqualTo(1);
    }
}
