package com.woowacourse.gongseek.vote.presentation.dto;

import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    public VoteResponse(Vote vote, List<VoteItemResponse> voteItems, Long votedItemId, boolean expired) {
        this.articleId = vote.getArticle().getId();
        this.voteItems = voteItems;
        this.votedItemId = votedItemId;
        this.expired = expired;
    }
}
