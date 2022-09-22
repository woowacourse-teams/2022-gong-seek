package com.woowacourse.gongseek.auth.infra;

import com.woowacourse.gongseek.auth.application.TokenProvider;
import com.woowacourse.gongseek.auth.exception.InvalidAccessTokenAtRenewException;
import com.woowacourse.gongseek.common.exception.UnAuthorizedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final long tokenValidityInMilliseconds;
    private final Key tokenSecretKey;

    public JwtTokenProvider(@Value("${security.jwt.access.expire-length}") long tokenValidityInMilliseconds,
                            @Value("${security.jwt.access.secret-key}") String tokenSecretKey) {
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.tokenSecretKey = Keys.hmacShaKeyFor(tokenSecretKey.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public String createAccessToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(tokenSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getAccessTokenPayload(String token) {
        return getClaimsJws(token, tokenSecretKey)
                .getBody()
                .getSubject();
    }

    @Override
    public boolean isValidAccessToken(String token) {
        try {
            Jws<Claims> claims = getClaimsJws(token, tokenSecretKey);

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void isValidAccessTokenWithTimeOut(String token) {
        try {
            getClaimsJws(token, tokenSecretKey).getBody();
            throw new UnAuthorizedTokenException();
        } catch (ExpiredJwtException ignored) {
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidAccessTokenAtRenewException();
        }
    }

    private Jws<Claims> getClaimsJws(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
