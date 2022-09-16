package com.woowacourse.gongseek.auth.utils;

import java.util.UUID;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private static final int MAX_AGE = 7 * 24 * 60 * 60;

    public static ResponseCookie create(UUID refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken.toString())
                .httpOnly(true)
                .path("/")
                .maxAge(MAX_AGE)
                .secure(true)
                .sameSite(SameSite.NONE.attributeValue())
                .build();
    }
}
