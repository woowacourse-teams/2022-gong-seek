package com.woowacourse.gongseek.vote.application.dto;

import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.repository.dto.VoteItemDto;
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

    private List<VoteItemDto> voteItems;

    private Long votedItemId;

    private boolean expired;

    public static VoteResponse of(Vote vote, List<VoteItemDto> voteItems, Long votedItemId) {
        return new VoteResponse(vote.getArticle().getId(), voteItems, votedItemId, vote.isExpired());
    }
}
