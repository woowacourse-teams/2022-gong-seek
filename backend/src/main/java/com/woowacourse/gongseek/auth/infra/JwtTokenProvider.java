package com.woowacourse.gongseek.auth.infra;

import com.woowacourse.gongseek.auth.application.TokenProvider;
import com.woowacourse.gongseek.auth.config.AccessTokenProperty;
import com.woowacourse.gongseek.auth.config.TokenProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    public String getAccessTokenPayload(String token) {
        return getClaimsJws(token, accessTokenProperty.getTokenSecretKey())
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

    public boolean isValidOnlyClaims(String token) {
        try {
            getClaimsJws(token, accessTokenProperty.getTokenSecretKey()).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
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
