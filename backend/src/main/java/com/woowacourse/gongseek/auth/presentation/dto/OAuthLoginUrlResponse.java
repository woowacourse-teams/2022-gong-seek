package com.woowacourse.gongseek.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuthLoginUrlResponse {

    private final String url;
}
