package com.woowacourse.gongseek.auth.config;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenProperty implements TokenProperty {
    private final long tokenValidityInMilliseconds;
    private final Key tokenSecretKey;

    public AccessTokenProperty(@Value("${security.jwt.access.expire-length}") long tokenValidityInMilliseconds,
                               @Value("${security.jwt.access.secret-key}") String tokenSecretKey) {
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.tokenSecretKey = Keys.hmacShaKeyFor(tokenSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public long getTokenValidityInMilliseconds() {
        return tokenValidityInMilliseconds;
    }

    @Override
    public Key getTokenSecretKey() {
        return tokenSecretKey;
    }
}
