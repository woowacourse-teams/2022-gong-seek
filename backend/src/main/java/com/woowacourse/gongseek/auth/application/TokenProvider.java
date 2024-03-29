package com.woowacourse.gongseek.auth.application;

public interface TokenProvider {

    String createAccessToken(String payload);

    String getAccessTokenPayload(String token);

    boolean isValidAccessToken(String token);

    void isValidAccessTokenWithTimeOut(String token);
}
