package com.woowacourse.gongseek.auth.utils;

import com.woowacourse.gongseek.auth.exception.EmptyTokenException;
import com.woowacourse.gongseek.auth.exception.InvalidTokenTypeException;
import java.util.Objects;

public class TokenExtractor {

    private static final String TOKEN_TYPE = "Bearer ";

    public static String extract(String token) {
        validateNullToken(token);
        validateTokenType(token);
        return token.substring(TOKEN_TYPE.length());
    }

    private static void validateNullToken(String token) {
        if (Objects.isNull(token)) {
            throw new EmptyTokenException();
        }
    }

    private static void validateTokenType(String token) {
        if (!token.toLowerCase().startsWith(TOKEN_TYPE.toLowerCase())) {
            throw new InvalidTokenTypeException();
        }
    }
}
