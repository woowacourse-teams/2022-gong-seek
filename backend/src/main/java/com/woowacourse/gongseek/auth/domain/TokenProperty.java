package com.woowacourse.gongseek.auth.domain;

import java.security.Key;

public interface TokenProperty {

    long getTokenValidityInMilliseconds();

    Key getTokenSecretKey();
}
