package com.woowacourse.gongseek.auth.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.woowacourse.gongseek.auth.application.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
class TokenExtractorTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void 토큰이_없는_경우_예외가_발생한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(null);

        assertThatThrownBy(() -> TokenExtractor.extract(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("토큰이 없습니다.");
    }

    @Test
    void 토큰_타입이_잘못된_경우_예외가_발생한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "Basic " + jwtTokenProvider.createToken("payload");

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(token);

        assertThatThrownBy(() -> TokenExtractor.extract(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("토큰 타입이 잘못되었습니다.");
    }

    @Test
    void 토큰_정보를_추출한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "Bearer " + jwtTokenProvider.createToken("payload");

        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(token);

        assertThat("Bearer " + TokenExtractor.extract(request)).isEqualTo(token);
    }
}
