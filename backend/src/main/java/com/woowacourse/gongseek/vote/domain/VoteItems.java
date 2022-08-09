package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.InvalidVoteItemCountException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class VoteItems {

    private static final int MIN_VOTE_ITEM_COUNT = 2;
    private static final int MAX_VOTE_ITEM_COUNT = 5;

    private final Set<VoteItem> voteItems;

    public VoteItems(Set<VoteItem> voteItems) {
        validateCount(voteItems);
        this.voteItems = voteItems;
    }

    public static VoteItems of(Set<String> voteItems, Vote vote) {
        Set<VoteItem> collect = voteItems.stream()
                .map(voteItem -> new VoteItem(voteItem, vote))
                .collect(Collectors.toSet());

        return new VoteItems(collect);
    }

    private void validateCount(Set<VoteItem> voteItems) {
        if (voteItems.size() < MIN_VOTE_ITEM_COUNT || voteItems.size() > MAX_VOTE_ITEM_COUNT) {
            throw new InvalidVoteItemCountException(voteItems.size());
        }
    }
}
