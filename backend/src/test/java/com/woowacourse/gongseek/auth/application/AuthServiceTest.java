package com.woowacourse.gongseek.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void 리다이렉트_URL_을_반환한다() {
        OAuthLoginUrlResponse oAuthUrl = authService.getLoginUrl();

        assertThat(oAuthUrl).isNotNull();
    }
}
