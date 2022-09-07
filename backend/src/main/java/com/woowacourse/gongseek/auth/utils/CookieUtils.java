package com.woowacourse.gongseek.auth.utils;

import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private static final int MAX_AGE = 7 * 24 * 60 * 60;

    public static ResponseCookie create(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(MAX_AGE)
                .secure(true)
                .sameSite("${cookie.samesite}")
                .build();
    }
}
