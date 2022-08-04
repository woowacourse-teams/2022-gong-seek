package com.woowacourse.gongseek.vote.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.vote.domain.Vote;
import java.util.List;
import lombok.Getter;

@Getter
public class VoteResponse {

    private final Long articleId;

    private final List<VoteItemResponse> voteItems;

    @JsonProperty(namespace = "isExpired")
    private boolean isExpired;

    public VoteResponse(Vote vote, List<VoteItemResponse> voteItems, boolean isExpired) {
        this.articleId = vote.getArticle().getId();
        this.voteItems = voteItems;
        this.isExpired = isExpired;
    }
}
