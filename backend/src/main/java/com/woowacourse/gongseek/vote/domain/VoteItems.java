package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.InvalidVoteItemCountException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VoteItems {

    private static final int MIN_VOTE_ITEM_COUNT = 2;
    private static final int MAX_VOTE_ITEM_COUNT = 5;

    private final Set<VoteItem> voteItems;

    public VoteItems(Set<VoteItem> voteItems) {
        validateCount(voteItems);
        this.voteItems = new LinkedHashSet<>(voteItems);
    }

    public static VoteItems of(List<String> voteItems, Vote vote) {
        Set<VoteItem> collect = voteItems.stream()
                .map(voteItem -> new VoteItem(voteItem, vote))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new VoteItems(collect);
    }

    private void validateCount(Set<VoteItem> voteItems) {
        if (voteItems.size() < MIN_VOTE_ITEM_COUNT || voteItems.size() > MAX_VOTE_ITEM_COUNT) {
            throw new InvalidVoteItemCountException(voteItems.size());
        }
    }

    public Set<VoteItem> getVoteItems() {
        return new LinkedHashSet<>(voteItems);
    }
}
