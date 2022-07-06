package com.woowacourse.gongseek.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponse {

    private final String accessToken;
}
