package com.woowacourse.gongseek.auth.application;

public interface TokenProvider {

    String createAccessToken(String payload);

    String createRefreshToken(String payload);

    String getAccessTokenPayload(String token);

    String getRefreshTokenPayload(String token);

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token);
}
