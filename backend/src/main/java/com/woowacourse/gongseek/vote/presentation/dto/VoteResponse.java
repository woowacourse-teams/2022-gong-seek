package com.woowacourse.gongseek.vote.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class VoteResponse {

    private final Long articleId;

    private final List<VoteItemResponse> voteItems;

    private final Long votedItemId;

    @JsonProperty(namespace = "isExpired")
    private boolean isExpired;

    public VoteResponse(Vote vote, List<VoteItemResponse> voteItems, VoteHistory voteHistory, boolean isExpired) {
        this.articleId = vote.getArticle().getId();
        this.voteItems = voteItems;
        this.votedItemId = getVotedItemIdOrNull(voteHistory);
        this.isExpired = isExpired;
    }

    private Long getVotedItemIdOrNull(VoteHistory voteHistory) {
        if (Objects.isNull(voteHistory)) {
            return null;
        }
        return voteHistory.getVoteItemId();
    }
}
