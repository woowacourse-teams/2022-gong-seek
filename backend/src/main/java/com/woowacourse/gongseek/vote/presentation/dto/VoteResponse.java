package com.woowacourse.gongseek.vote.presentation.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class VoteResponse {

    private Long articleId;

    private List<VoteItemResponse> voteItems;

    private Long votedItemId;

    private boolean expired;
}
