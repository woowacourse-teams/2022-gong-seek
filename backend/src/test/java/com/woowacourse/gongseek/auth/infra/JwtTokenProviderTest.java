package com.woowacourse.gongseek.auth.infra;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.auth.application.TokenProvider;
import com.woowacourse.gongseek.auth.domain.AccessTokenProperty;
import com.woowacourse.gongseek.auth.domain.RefreshTokenProperty;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class JwtTokenProviderTest {

    private static final Long EXPIRE_TIME = 86400000L;
    private static final String SECRET_KEY = "thisIsTestSecretKey-thisIsTestSecretKey-thisIsTestSecretKey";
    private static final Long REFRESH_EXPIRE_TIME = 60 * 1000L;
    private static final String REFRESH_SECRET_KEY = "thisIsTestRfrestSecretKey-thisIsTestSecretKey-thisIsTestSecretKey";

    private final TokenProvider jwtTokenProvider = new JwtTokenProvider(
            new AccessTokenProperty(EXPIRE_TIME, SECRET_KEY),
            new RefreshTokenProperty(REFRESH_EXPIRE_TIME, REFRESH_SECRET_KEY));

    @Test
    void 토큰을_생성한다() {
        String token = jwtTokenProvider.createAccessToken("1L");

        assertThat(token).isNotNull();
    }

    @Test
    void 잘못된_토큰_검사() {
        assertThat(jwtTokenProvider.isValidAccessToken("thiIsInvalidToken")).isFalse();
    }

    @Test
    void 만료된_엑세스_토큰_검사() {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setSubject("1L")
                .setExpiration(new Date(new Date().getTime() - 1))
                .compact();

        assertThat(jwtTokenProvider.isValidAccessToken(token)).isFalse();
    }

    @Test
    void 엑세스_토큰_페이로드_확인() {
        String payload = "payload";
        String token = jwtTokenProvider.createAccessToken(payload);

        assertThat(jwtTokenProvider.getAccessTokenPayload(token)).isEqualTo(payload);
    }
}
