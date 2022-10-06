package com.woowacourse.gongseek.auth.utils;

import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private static final int CREATE_AGE = 7 * 24 * 60 * 60;
    private static final int DELETE_AGE = 0;
    private static final String COOKIE_NAME = "refreshToken";
    private static final String EMPTY_VALUE = "";

    public static ResponseCookie create(Long refreshToken) {
        return ResponseCookie.from(COOKIE_NAME, refreshToken.toString())
                .httpOnly(true)
                .path("/")
                .maxAge(CREATE_AGE)
                .secure(true)
                .sameSite(SameSite.NONE.attributeValue())
                .build();
    }

    public static ResponseCookie delete() {
        return ResponseCookie.from(COOKIE_NAME, EMPTY_VALUE)
                .httpOnly(true)
                .path("/")
                .maxAge(DELETE_AGE)
                .secure(true)
                .sameSite(SameSite.NONE.attributeValue())
                .build();
    }
}
