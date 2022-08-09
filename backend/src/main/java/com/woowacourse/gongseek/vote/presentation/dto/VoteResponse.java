package com.woowacourse.gongseek.vote.presentation.dto;

import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.util.List;
import java.util.stream.Collectors;
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

    public static VoteResponse of(Long articleId, List<VoteItem> voteItems, Long votedItemId, boolean expired) {
        return new VoteResponse(articleId, convertVoteItemResponse(voteItems), votedItemId, expired);
    }

    private static List<VoteItemResponse> convertVoteItemResponse(List<VoteItem> voteItems) {
        return voteItems.stream()
                .map(VoteItemResponse::new)
                .collect(Collectors.toList());
    }
}
