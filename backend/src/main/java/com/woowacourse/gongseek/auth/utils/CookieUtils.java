package com.woowacourse.gongseek.auth.utils;

import com.woowacourse.gongseek.auth.exception.EmptyCookieException;
import java.util.Objects;
import javax.servlet.http.Cookie;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private static final int MAX_AGE = 7 * 24 * 60 * 60;

    public static void validateCookie(Cookie cookie) {
        if (Objects.isNull(cookie)) {
            throw new EmptyCookieException();
        }
    }

    public static ResponseCookie create(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(MAX_AGE)
                .secure(true)
                .sameSite(SameSite.STRICT.attributeValue())
                .build();
    }
}
