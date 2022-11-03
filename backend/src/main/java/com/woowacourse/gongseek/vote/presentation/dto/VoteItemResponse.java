package com.woowacourse.gongseek.vote.presentation.dto;

import com.woowacourse.gongseek.vote.domain.VoteItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class VoteItemResponse {

    private Long id;
    private String content;
    private int amount;

    public VoteItemResponse(VoteItem voteItem) {
        this(voteItem.getId(), voteItem.getContent().getValue(), 1);
    }
}
