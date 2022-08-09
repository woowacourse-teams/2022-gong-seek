package com.woowacourse.gongseek.like.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikeResponse {

    private Boolean isLike;
    private Long likeCount;
}
