package com.woowacourse.gongseek.auth.application;

public interface TokenProvider {

    String createAccessToken(String payload);

    String createRefreshToken(String payload);

    String getAccessTokenPayload(String token);

    String getRefreshTokenPayload(String token);

    boolean isValidAccessToken(String token);

    boolean isValidRefreshToken(String token);
}
