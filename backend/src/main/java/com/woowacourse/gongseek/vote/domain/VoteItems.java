package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.InvalidVoteItemCountException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class VoteItems {

    private final Set<VoteItem> voteItems;

    public static VoteItems of(Set<String> voteItems, Vote vote) {
        Set<VoteItem> collect = voteItems.stream()
                .map(voteItem -> new VoteItem(voteItem, vote))
                .collect(Collectors.toSet());

        return new VoteItems(collect);
    }

    public VoteItems(Set<VoteItem> voteItems) {
        validateSize(voteItems);
        this.voteItems = voteItems;
    }

    private void validateSize(Set<VoteItem> voteItems) {
        if (voteItems.size() < 2 || voteItems.size() > 5) {
            throw new InvalidVoteItemCountException();
        }
    }

}
