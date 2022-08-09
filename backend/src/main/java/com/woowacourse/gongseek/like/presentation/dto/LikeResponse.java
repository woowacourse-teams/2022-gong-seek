package com.woowacourse.gongseek.like.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikeResponse {

    @JsonProperty("isLike")
    private Boolean isLike;

    private Long likeCount;
}
