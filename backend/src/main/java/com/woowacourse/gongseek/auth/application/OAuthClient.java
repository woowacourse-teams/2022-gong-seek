package com.woowacourse.gongseek.auth.application;

import com.woowacourse.gongseek.auth.presentation.dto.GithubProfileResponse;

public interface OAuthClient {

    String getRedirectUrl();

    GithubProfileResponse getMemberProfile(String code);
}
