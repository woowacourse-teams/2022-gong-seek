package com.woowacourse.gongseek.auth.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.auth.exception.EmptyTokenException;
import com.woowacourse.gongseek.auth.exception.InvalidTokenTypeException;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class TokenExtractorTest {

    private static final Long EXPIRE_TIME = 86400000L;
    private static final String SECRET_KEY = "thisIsTestSecretKey-thisIsTestSecretKey-thisIsTestSecretKey";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(EXPIRE_TIME, SECRET_KEY);

    @Test
    void 토큰이_없는_경우_예외가_발생한다() {
        String token = null;

        assertThatThrownBy(() -> TokenExtractor.extract(token))
                .isInstanceOf(EmptyTokenException.class)
                .hasMessage("토큰이 존재하지 않습니다.");
    }

    @Test
    void 토큰_타입이_잘못된_경우_예외가_발생한다() {
        String token = "Basic " + jwtTokenProvider.createToken("payload");

        assertThatThrownBy(() -> TokenExtractor.extract(token))
                .isInstanceOf(InvalidTokenTypeException.class)
                .hasMessage("토큰 타입이 올바르지 않습니다.");
    }

    @Test
    void 토큰_정보를_추출한다() {
        String token = "Bearer " + jwtTokenProvider.createToken("payload");

        assertThat("Bearer " + TokenExtractor.extract(token)).isEqualTo(token);
    }
}
