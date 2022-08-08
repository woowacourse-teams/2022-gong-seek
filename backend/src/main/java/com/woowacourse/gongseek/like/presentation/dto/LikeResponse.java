package com.woowacourse.gongseek.like.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikeResponse {

    @JsonProperty("isLike")
    @Accessors(fluent = true)
    private boolean isLike;

    private int likeCount;
}
