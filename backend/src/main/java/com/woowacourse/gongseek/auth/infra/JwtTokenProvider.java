package com.woowacourse.gongseek.auth.infra;

import com.woowacourse.gongseek.auth.application.TokenProvider;
import com.woowacourse.gongseek.auth.domain.AccessTokenProperty;
import com.woowacourse.gongseek.auth.domain.RefreshTokenProperty;
import com.woowacourse.gongseek.auth.domain.TokenProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements TokenProvider {

    private final AccessTokenProperty accessTokenProperty;
    private final RefreshTokenProperty refreshTokenProperty;

    @Override
    public String createAccessToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        return generateToken(claims, accessTokenProperty);
    }

    private String generateToken(Claims claims, TokenProperty tokenProperty) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenProperty.getTokenValidityInMilliseconds());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(tokenProperty.getTokenSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String createRefreshToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        return generateToken(claims, refreshTokenProperty);
    }

    @Override
    public String getAccessTokenPayload(String token) {
        return getClaimsJws(token, accessTokenProperty.getTokenSecretKey())
                .getBody()
                .getSubject();
    }

    @Override
    public String getRefreshTokenPayload(String token) {
        return getClaimsJws(token, refreshTokenProperty.getTokenSecretKey())
                .getBody()
                .getSubject();
    }

    @Override
    public boolean isValidAccessToken(String token) {
        try {
            Jws<Claims> claims = getClaimsJws(token, accessTokenProperty.getTokenSecretKey());

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isValidRefreshToken(String token) {
        try {
            Jws<Claims> claims = getClaimsJws(token, refreshTokenProperty.getTokenSecretKey());

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaimsJws(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
