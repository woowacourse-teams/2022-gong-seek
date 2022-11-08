package com.woowacourse.gongseek.vote.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class SelectVoteItemIdRequest {

    private Long voteItemId;
}
