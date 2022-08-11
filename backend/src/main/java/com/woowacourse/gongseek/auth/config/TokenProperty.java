package com.woowacourse.gongseek.auth.config;

import java.security.Key;

public interface TokenProperty {

    long getTokenValidityInMilliseconds();

    Key getTokenSecretKey();
}
