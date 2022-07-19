package com.woowacourse.gongseek.auth.utils;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class TokenExtractor {

    private static final String TOKEN_TYPE = "Bearer ";

    public static String extract(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        validateNullToken(token);
        validateTokenType(token);

        return token.substring(TOKEN_TYPE.length());
    }

    public static String extract(String token) {
        return token.substring(TOKEN_TYPE.length());
    }

    private static void validateNullToken(String token) {
        if (Objects.isNull(token)) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
    }

    private static void validateTokenType(String token) {
        if (!token.toLowerCase().startsWith(TOKEN_TYPE.toLowerCase())) {
            throw new IllegalArgumentException("토큰 타입이 잘못되었습니다.");
        }
    }
}
