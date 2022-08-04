package com.woowacourse.gongseek.vote.presentation.dto;

import com.woowacourse.gongseek.vote.domain.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteItemResponse {

    private Long id;
    private String content;
    private int amount;

    public VoteItemResponse(VoteItem voteItem) {
        this(voteItem.getId(), voteItem.getContent().getValue(), voteItem.getAmount().getValue());
    }
}
