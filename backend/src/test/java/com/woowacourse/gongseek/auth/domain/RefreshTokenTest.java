package com.woowacourse.gongseek.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {


    @Test
    void 현재날짜로부터_7일까지_유효한_리프레시토큰을_만든다() {
        RefreshToken refreshToken = RefreshToken.create(1L);
        assertAll(
                () -> assertThat(refreshToken.getExpiryDate().toLocalDate()).isEqualTo(LocalDate.now().plusDays(7L)),
                () -> assertThat(refreshToken.isExpired()).isFalse()
        );
    }

    @Test
    void 처음_리프레시토큰은_issue가_false이고_한번_사용하면_trrue가_된다() {
        RefreshToken refreshToken = RefreshToken.create(1L);
        assertThat(refreshToken.isIssue()).isFalse();
        refreshToken.used();
        assertThat(refreshToken.isIssue()).isTrue();
    }
}
